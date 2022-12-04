/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql_3306
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : score

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 04/12/2022 22:07:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `config_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '参数id',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键名',
  `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (3000, '最大生成用户数量', 'user.max_generate_number', '30', '2022-12-04 18:06:51', '管理员', NULL, NULL);
INSERT INTO `config` VALUES (3001, '最长生成用户名长度', 'user.max_generate_username_length', '6', '2022-12-04 18:07:21', '管理员', NULL, NULL);
INSERT INTO `config` VALUES (3002, '生成用户数量至少达到多少才需要主评委', 'user.least_generate_number_to_lead_judge', '5', '2022-12-04 21:43:17', '管理员', NULL, NULL);

-- ----------------------------
-- Table structure for contestant
-- ----------------------------
DROP TABLE IF EXISTS `contestant`;
CREATE TABLE `contestant`  (
  `contestant_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '参赛对象id',
  `match_id` bigint(11) NOT NULL COMMENT '比赛id',
  `match_order` int(4) NULL DEFAULT NULL COMMENT '比赛顺序',
  `is_open` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否开启评分通道',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参赛者名称',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参赛者简介',
  `score` double(11, 0) NULL DEFAULT NULL COMMENT '得分',
  `rank` int(11) NULL DEFAULT NULL COMMENT '排名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`contestant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10009 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参赛对象表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contestant
-- ----------------------------
INSERT INTO `contestant` VALUES (10000, 2000, 2, '0', '参赛者1', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10001, 2000, 7, '0', '参赛者2', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10002, 2000, 8, '0', '参赛者3', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10003, 2000, 4, '0', '参赛者4', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10004, 2000, 3, '0', '参赛者5', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10005, 2000, 1, '0', '参赛者6', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10006, 2000, 5, '0', '参赛者7', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10007, 2000, 6, '0', '参赛者8', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (10008, 2000, 9, '0', '参赛者9', '简介xxxx', NULL, NULL, '2022-11-24 23:40:12', 'xxxxx活动负责人', NULL, NULL);

-- ----------------------------
-- Table structure for match
-- ----------------------------
DROP TABLE IF EXISTS `match`;
CREATE TABLE `match`  (
  `match_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '比赛id',
  `match_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '比赛名称',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `max_score` double(11, 0) NULL DEFAULT 100 COMMENT '最高分，默认100',
  `score_rule_id` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评分规则id（\'1\'代表求总分，\'2\'代表求平均分，\'3\'代表求去掉最低最高后的平均分）',
  `score_rule_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评分规则名称',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`match_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '比赛信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of match
-- ----------------------------
INSERT INTO `match` VALUES (2000, '第十一届校园十大歌手比赛', '简介xxxxx', 100, NULL, '去掉最低分，去掉最高分，求平均分', '2022-11-23 22:30:02', '2023-01-13 22:30:07', '2022-11-10 10:25:06', 'xxxxx活动负责人', NULL, NULL);

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `match_id` bigint(11) NOT NULL COMMENT '比赛id',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `contestant_id` bigint(11) NOT NULL COMMENT '参赛者id',
  `score` double(11, 0) NULL DEFAULT NULL COMMENT '得分',
  `turn` int(11) NULL DEFAULT 0 COMMENT '回合（默认0）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`match_id`, `user_id`, `contestant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评委评分表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (2000, 1001, 10001, 80, NULL, '2022-11-24 23:37:25', '', '2022-11-25 13:21:57', '');
INSERT INTO `score` VALUES (2000, 1002, 10000, 89, NULL, '2022-11-25 00:02:31', '', '2022-11-25 13:21:59', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户登录账号',
  `password` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0为超级管理员，1为活动负责人，2为主评委，3为评委',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1000, 'root', '123456', '0', '管理员', '2022-11-10 10:26:50', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1001, '748912', 'd92d12', '1', 'xxxxx活动负责人', '2022-11-09 19:57:13', 'root', NULL, NULL);
INSERT INTO `user` VALUES (1002, '389012', '123456', '2', 'xxxx活动主评委', '2022-11-13 10:25:44', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1003, '709751', '123456', '3', 'xxxx活动评委', '2022-11-24 22:31:05', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1004, '389123', '123456', '3', 'xxxx活动评委', '2022-11-24 22:31:06', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1005, '983145', '613756', '2', '第十一届校园十大歌手比赛1号主评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1006, '767975', '643564', '3', '第十一届校园十大歌手比赛2号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1007, '563092', '992978', '3', '第十一届校园十大歌手比赛3号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1008, '032680', '353107', '3', '第十一届校园十大歌手比赛4号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1009, '750888', '003433', '3', '第十一届校园十大歌手比赛5号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1010, '429744', '112777', '3', '第十一届校园十大歌手比赛6号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1011, '209138', '254103', '3', '第十一届校园十大歌手比赛7号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1012, '288708', '631936', '3', '第十一届校园十大歌手比赛8号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1013, '711879', '720466', '3', '第十一届校园十大歌手比赛9号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `user` VALUES (1014, '244052', '209937', '3', '第十一届校园十大歌手比赛10号评委', '2022-12-04 22:05:04', 'xxxxx活动负责人', NULL, NULL);

-- ----------------------------
-- Table structure for user_match
-- ----------------------------
DROP TABLE IF EXISTS `user_match`;
CREATE TABLE `user_match`  (
  `user_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `match_id` bigint(11) NOT NULL COMMENT '比赛id',
  PRIMARY KEY (`user_id`, `match_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户比赛表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_match
-- ----------------------------
INSERT INTO `user_match` VALUES (1001, 2000);
INSERT INTO `user_match` VALUES (1002, 2000);
INSERT INTO `user_match` VALUES (1003, 2000);
INSERT INTO `user_match` VALUES (1004, 2000);

SET FOREIGN_KEY_CHECKS = 1;
