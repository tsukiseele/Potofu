package com.tsukiseele.potofu.config

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.cache.interceptor.CacheResolver
import org.springframework.cache.interceptor.SimpleCacheErrorHandler
import org.springframework.cache.interceptor.SimpleCacheResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration


@Configuration
@EnableCaching
@PropertySource("classpath:application.properties")
open class RedisConfig: CachingConfigurerSupport() {
    @Value("\${spring.redis.database}")
    private val database = 0

    @Value("\${spring.redis.host}")
    private val host: String = ""

//    @Value("\${spring.redis.password}")
//    private val password: String = ""

    @Value("\${spring.redis.port}")
    private val port = 0

    @Value("\${spring.redis.timeout}")
    private val timeout = 0

    @Value("\${spring.redis.lettuce.pool.max-idle}")
    private val maxIdle = 0

    @Value("\${spring.redis.lettuce.pool.min-idle}")
    private val minIdle = 0

    @Value("\${spring.redis.lettuce.pool.max-active}")
    private val maxActive = 0

    @Value("\${spring.redis.lettuce.pool.max-wait}")
    private val maxWait = 0L

    @Bean
    open fun lettuceConnectionFactory(genericObjectPoolConfig: GenericObjectPoolConfig<*>): LettuceConnectionFactory {
        // 单机版配置
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.database = database
        redisStandaloneConfiguration.hostName = host
        redisStandaloneConfiguration.port = port
//        redisStandaloneConfiguration.password = RedisPassword.of(password)

        // 集群版配置
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        String[] serverArray = clusterNodes.split(",");
//        Set<RedisNode> nodes = new HashSet<RedisNode>();
//        for (String ipPort : serverArray) {
//            String[] ipAndPort = ipPort.split(":");
//            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
//        }
//        redisClusterConfiguration.setPassword(RedisPassword.of(password));
//        redisClusterConfiguration.setClusterNodes(nodes);
//        redisClusterConfiguration.setMaxRedirects(maxRedirects);
        val clientConfig: LettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout.toLong()))
                .poolConfig(genericObjectPoolConfig)
                .build()
        return LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig)
    }

    @Bean
    override fun cacheManager(): CacheManager? {
        val cacheConfiguration = generateCacheConfigMap()
        val redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                GenericJackson2JsonRedisSerializer()))
        return RedisCacheManager.builder(lettuceConnectionFactory(genericObjectPoolConfig()))
                .cacheDefaults(redisCacheConfig)
                .withInitialCacheConfigurations(cacheConfiguration)
                .build()
    }

    override fun cacheResolver(): CacheResolver? {
        return SimpleCacheResolver(cacheManager()!!)
    }

    override fun errorHandler(): CacheErrorHandler? {
        return SimpleCacheErrorHandler()
    }
    /**
     * GenericObjectPoolConfig 连接池配置
     *
     * @return GenericObjectPoolConfig<*>
     */
    @Bean
    open fun genericObjectPoolConfig(): GenericObjectPoolConfig<*> {
        val genericObjectPoolConfig: GenericObjectPoolConfig<*> = GenericObjectPoolConfig<Any?>()
        genericObjectPoolConfig.maxIdle = maxIdle
        genericObjectPoolConfig.minIdle = minIdle
        genericObjectPoolConfig.maxTotal = maxActive
        genericObjectPoolConfig.maxWaitMillis = maxWait
        return genericObjectPoolConfig
    }

    /**
     * generateCacheConfigMap
     * 创建缓存配置映射
     *
     * @return MutableMap<String, RedisCacheConfiguration>
     */
    private fun generateCacheConfigMap(): MutableMap<String, RedisCacheConfiguration> {
        val initialCacheConfiguration = mutableMapOf<String, RedisCacheConfiguration>()
        initialCacheConfiguration.put("hourCache",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1))) // 1小时
        initialCacheConfiguration.put("minCache",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1))) // 1分钟
        initialCacheConfiguration.put("dayCache",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1))) // 1天
        return initialCacheConfiguration
    }
}