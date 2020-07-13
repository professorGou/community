CREATE TABLE `notification` (
`id` INT NOT NULL AUTO_INCREMENT,
`notifier` INT NOT NULL COMMENT '通知者',
`receiver` INT NOT NULL COMMENT '接收者',
`outer_id` INT NOT NULL,
`type` INT NOT NULL COMMENT '类型',
`gmt_create` BIGINT NOT NULL COMMENT '创建时间',
`status` INT NOT NULL DEFAULT '0' COMMENT '状态，0默认未读，1已读',
`notifier_name` VARCHAR ( 100 ) NOT NULL,
`outer_title` VARCHAR ( 255 ) NOT NULL,
PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;