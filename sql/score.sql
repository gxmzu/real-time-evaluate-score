/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : score

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 10/11/2022 22:11:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `update_by` datetime(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`contestant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参赛对象表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contestant
-- ----------------------------
INSERT INTO `contestant` VALUES (1, 1, 1, '0', '参赛者1', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (2, 1, 2, '0', '参赛者2', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (3, 1, 3, '0', '参赛者3', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (4, 1, 4, '0', '参赛者4', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (5, 1, 5, '0', '参赛者5', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (6, 1, 6, '0', '参赛者6', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (7, 1, 7, '0', '参赛者7', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);
INSERT INTO `contestant` VALUES (8, 1, 8, '0', '参赛者8', '简介xxxx', NULL, NULL, '2022-11-10 10:27:39', 'xxxxx活动负责人', NULL, NULL);

-- ----------------------------
-- Table structure for match
-- ----------------------------
DROP TABLE IF EXISTS `match`;
CREATE TABLE `match`  (
  `match_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '比赛id',
  `match_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '比赛名称',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `max_score` int(255) NULL DEFAULT 100 COMMENT '最高分，默认100',
  `score_rule_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评分规则名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` datetime(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`match_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '比赛信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of match
-- ----------------------------
INSERT INTO `match` VALUES (1, '第十一届校园十大歌手比赛', '简介xxxxx', 100, '去掉最低分，去掉最高分，求平均分', '2022-11-10 10:25:06', 'xxxxx活动负责人', NULL, NULL);

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `user_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `match_id` bigint(20) NOT NULL COMMENT '比赛id',
  `contestant_id` bigint(11) NOT NULL COMMENT '参赛者id',
  `score` double(11, 0) NULL DEFAULT NULL COMMENT '得分',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` datetime(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`, `match_id`, `contestant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评委评分表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户登录账号',
  `user_pwd` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0为超级管理员，1为活动负责人，2为主评委，3为评委',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` datetime(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '123456', '0', NULL, NULL, '管理员', '2022-11-10 10:26:50', NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, '748912', 'd92d12', '1', '2022-11-09 19:56:48', '2022-11-12 19:56:54', 'xxxxx活动负责人', '2022-11-09 19:57:13', 'root', NULL, NULL);

-- ----------------------------
-- Table structure for user_match
-- ----------------------------
DROP TABLE IF EXISTS `user_match`;
CREATE TABLE `user_match`  (
  `user_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `match_id` bigint(11) NULL DEFAULT NULL COMMENT '比赛id',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户比赛表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_match
-- ----------------------------
INSERT INTO `user_match` VALUES (2, 1);

SET FOREIGN_KEY_CHECKS = 1;
