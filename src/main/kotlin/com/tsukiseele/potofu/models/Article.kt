package com.tsukiseele.potofu.models

import com.tsukiseele.kotlin.Noarg
import java.io.Serializable
import java.util.*

@Noarg
data class Article(
        var articleId: Int,
        var userId: Int,
        var articleTitle: String?,
        var articleSubtitle: String?,
        var articleTags: String?,
        var articleType: String?,
        var articleCover: String?,
        var articleContent: String?,
        var articleDate: Date?,
        var articleLastUpdate: Date?,
        var articleViews: Int,
        var articleCommentCount: Int
): Serializable