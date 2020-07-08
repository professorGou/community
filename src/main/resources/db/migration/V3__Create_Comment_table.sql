CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int NOT NULL COMMENT '父类ID',
  `type` int NOT NULL COMMENT '父类类型',
  `commentator` int NOT NULL COMMENT '评论人ID',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '更新时间',
  `like_count` bigint DEFAULT '0' COMMENT '点赞数',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `comment_count` int DEFAULT '0' COMMENT '评论数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;