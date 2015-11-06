

-- 用户表: users --
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `passwd` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `type` tinyint(1) DEFAULT NULL COMMENT '用户类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '用户状态',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_users_email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `idx_users_nick_UNIQUE` (`nickname` ASC),
  UNIQUE INDEX `idx_users_mobile_UNIQUE` (`mobile` ASC)
);

-- 用户信息表: ecp_user_profiles
DROP TABLE IF EXISTS `ecp_user_profiles`;
CREATE TABLE `ecp_user_profiles` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `realname` varchar(10) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别,0男,1女',
  `province_id` int(11) DEFAULT NULL COMMENT '省id',
  `province` varchar(64) DEFAULT NULL COMMENT '所在省',
  `city_id` int(11) DEFAULT NULL COMMENT '城市id',
  `city` varchar(64) DEFAULT NULL COMMENT '所在市',
  `region_id` int(11) DEFAULT NULL COMMENT '区id',
  `region` varchar(64) DEFAULT NULL COMMENT '所在区',
  `street` varchar(128) DEFAULT NULL COMMENT '地址',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `extra` varchar(2048) DEFAULT NULL COMMENT '额外信息json',
  `channel` varchar(64) DEFAULT NULL COMMENT '用户注册渠道',
  `avatar` varchar(128) DEFAULT NULL COMMENT '用户头像',
  `birth` char(10) DEFAULT NULL COMMENT '出生日期',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
);

-- 第三方用户表: ecp_third_party_users
DROP TABLE IF EXISTS `ecp_third_party_users`;
CREATE TABLE `ecp_third_party_users` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `corp_id` bigint(20) DEFAULT NULL COMMENT '企业id',
  `third_part_id` varchar(20) DEFAULT NULL COMMENT '第三方用户id',
  `third_part_type` smallint(6) DEFAULT NULL COMMENT '第三方用户类型',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- 用户图片表: ecp_user_images
DROP TABLE IF EXISTS `ecp_user_images`;
CREATE TABLE `ecp_user_images` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `path` varchar(128) NOT NULL DEFAULT '' COMMENT '图片相对路径',
  `size` int(11) NOT NULL COMMENT '图片大小',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='用户图片表';
CREATE INDEX idx_user_images_user_id ON ecp_user_images (user_id);