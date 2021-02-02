create database vueadmindb default character set utf8mb4 collate utf8mb4_unicode_ci;
CREATE USER 'vueadmin'@'%' IDENTIFIED BY 'vueadmin.1qaz';

grant all privileges on vueadmindb.* to 'vueadmin'@'%';


CREATE TABLE `vue_user` (
                            `user_id` BIGINT NOT NULL AUTO_INCREMENT,
                            `username` VARCHAR(32) NOT NULL,
                            `password` VARCHAR(64) NOT NULL,
                            `create_date` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
                            `status_date` datetime NOT NULL DEFAULT now() COMMENT '状态时间',
                            `status` INT NOT NULL DEFAULT '0' COMMENT '状态',
                            PRIMARY KEY (`user_id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `vue_role` (
                            `role_id` BIGINT NOT NULL AUTO_INCREMENT,
                            `role_name` VARCHAR(32) NOT NULL DEFAULT '用户' COMMENT '角色名称',
                            `role_desc` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '角色描述',
                            `create_date` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
                            `status_date` datetime NOT NULL DEFAULT now() COMMENT '状态时间',
                            `status` INT NOT NULL DEFAULT '0' COMMENT '状态',
                            PRIMARY KEY (`role_id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;


CREATE TABLE `vue_user_role` (
                                 `user_role_id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `role_id` BIGINT NOT NULL,
                                 `user_id` BIGINT NOT NULL,
                                 `create_date` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
                                 `status_date` datetime NOT NULL DEFAULT now() COMMENT '状态时间',
                                 `status` INT NOT NULL DEFAULT '0' COMMENT '状态',
                                 PRIMARY KEY (`user_role_id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `vue_permission` (
                                  `permission_id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `permission_name` VARCHAR(32) NOT NULL DEFAULT '用户' COMMENT '权限名称',
                                  `permission_desc` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '权限描述',
                                  `permission_type` INT NOT NULL DEFAULT '0' COMMENT '权限类型',
                                  `parent_permission_id` BIGINT NOT NULL DEFAULT '0' COMMENT '父权限',
                                  `create_date` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
                                  `status_date` datetime NOT NULL DEFAULT now() COMMENT '状态时间',
                                  `status` INT NOT NULL DEFAULT '0' COMMENT '状态',
                                  PRIMARY KEY (`permission_id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;

CREATE TABLE `vue_role_permission` (
                                       `role_permission_id` BIGINT NOT NULL AUTO_INCREMENT,
                                       `role_id` BIGINT NOT NULL,
                                       `permission_id` BIGINT NOT NULL ,
                                       `create_date` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
                                       `status_date` datetime NOT NULL DEFAULT now() COMMENT '状态时间',
                                       `status` INT NOT NULL DEFAULT '0' COMMENT '状态',
                                       PRIMARY KEY (`role_permission_id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;
