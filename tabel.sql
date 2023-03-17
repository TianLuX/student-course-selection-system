/*
 Navicat Premium Data Transfer

 Source Server         : hello
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : tabel

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 23/12/2021 09:14:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '2020003', '14e1b600b1fd579f47433b88e8d85291', '张三');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `cid` int(0) NOT NULL AUTO_INCREMENT,
  `cName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tid` int(0) NULL DEFAULT NULL,
  `cTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `tech` (`tid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (7, 'web设计', 1, '周一12');
INSERT INTO `course` VALUES (8, '数据结构', 1, '周二12');
INSERT INTO `course` VALUES (9, '数据结构', 2, '周二34');
INSERT INTO `course` VALUES (10, '高数', 3, '周三34');
INSERT INTO `course` VALUES (11, '高数', 1, '周一56');
INSERT INTO `course` VALUES (12, '计组', 3, '周二12');
INSERT INTO `course` VALUES (13, '测试课程', 2, '周一78');

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`  (
  `scid` int(0) NOT NULL AUTO_INCREMENT,
  `stdid` int(0) NULL DEFAULT NULL,
  `cid` int(0) NULL DEFAULT NULL,
  `score` decimal(5, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`scid`) USING BTREE,
  INDEX `stdid`(`stdid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  CONSTRAINT `sc_ibfk_1` FOREIGN KEY (`stdid`) REFERENCES `std` (`stdid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sc_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 278 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES (256, 5, 8, NULL);
INSERT INTO `sc` VALUES (257, 5, 10, NULL);
INSERT INTO `sc` VALUES (258, 5, 11, NULL);
INSERT INTO `sc` VALUES (259, 10, 7, 80.00);
INSERT INTO `sc` VALUES (260, 10, 8, NULL);
INSERT INTO `sc` VALUES (261, 10, 10, NULL);
INSERT INTO `sc` VALUES (262, 15, 10, NULL);
INSERT INTO `sc` VALUES (263, 15, 12, NULL);
INSERT INTO `sc` VALUES (265, 4, 8, NULL);
INSERT INTO `sc` VALUES (266, 4, 11, NULL);
INSERT INTO `sc` VALUES (267, 4, 13, NULL);
INSERT INTO `sc` VALUES (275, 1, 7, 80.00);
INSERT INTO `sc` VALUES (276, 1, 10, NULL);
INSERT INTO `sc` VALUES (277, 1, 13, NULL);

-- ----------------------------
-- Table structure for sc_copy
-- ----------------------------
DROP TABLE IF EXISTS `sc_copy`;
CREATE TABLE `sc_copy`  (
  `scid` int(0) NOT NULL AUTO_INCREMENT,
  `stdid` int(0) NULL DEFAULT NULL,
  `cid` int(0) NULL DEFAULT NULL,
  `score` decimal(5, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`scid`) USING BTREE,
  INDEX `stdid`(`stdid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  CONSTRAINT `sc_copy_ibfk_1` FOREIGN KEY (`stdid`) REFERENCES `std` (`stdid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sc_copy_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 278 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sc_copy
-- ----------------------------
INSERT INTO `sc_copy` VALUES (256, 5, 8, NULL);
INSERT INTO `sc_copy` VALUES (257, 5, 10, NULL);
INSERT INTO `sc_copy` VALUES (258, 5, 11, NULL);
INSERT INTO `sc_copy` VALUES (259, 10, 7, 80.00);
INSERT INTO `sc_copy` VALUES (260, 10, 8, NULL);
INSERT INTO `sc_copy` VALUES (261, 10, 10, NULL);
INSERT INTO `sc_copy` VALUES (262, 15, 10, NULL);
INSERT INTO `sc_copy` VALUES (263, 15, 12, NULL);
INSERT INTO `sc_copy` VALUES (265, 4, 8, NULL);
INSERT INTO `sc_copy` VALUES (266, 4, 11, NULL);
INSERT INTO `sc_copy` VALUES (267, 4, 13, NULL);
INSERT INTO `sc_copy` VALUES (275, 1, 7, 80.00);
INSERT INTO `sc_copy` VALUES (276, 1, 10, NULL);
INSERT INTO `sc_copy` VALUES (277, 1, 13, NULL);

-- ----------------------------
-- Table structure for std
-- ----------------------------
DROP TABLE IF EXISTS `std`;
CREATE TABLE `std`  (
  `stdid` int(0) NOT NULL AUTO_INCREMENT,
  `stdName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stdNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stdPwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stdPhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stdEmail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stdAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stdid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of std
-- ----------------------------
INSERT INTO `std` VALUES (1, '谢天露', '202000300070', '14e1b600b1fd579f47433b88e8d85291', '13968892959', '2794658200@com', 'abcdfe');
INSERT INTO `std` VALUES (3, '李四', '202000300', '14e1b600b1fd579f47433b88e8d85291', '13968892700', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (4, 'Alexander', '201012345678', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (5, '何琛', '202413623637', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (6, 'Thompson', '202413823632', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (7, 'Solomon', '201023354678', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (8, '以梅', '202413623637', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (9, '老梅', '202413852632', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (10, 'Vivienne', '202413723639', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (12, 'lala', '202413624638', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (13, '花花', '202000300078', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (14, 'savannah', '201014643673', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (15, 'Alexander', '201012354678', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');
INSERT INTO `std` VALUES (16, '思思', '201013533367', '14e1b600b1fd579f47433b88e8d85291', '13968892795', '2794658200@qq.com', 'abcdfe');

-- ----------------------------
-- Table structure for tech
-- ----------------------------
DROP TABLE IF EXISTS `tech`;
CREATE TABLE `tech`  (
  `tid` int(0) NOT NULL AUTO_INCREMENT,
  `tName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tech
-- ----------------------------
INSERT INTO `tech` VALUES (1, '李华', '2020003', '14e1b600b1fd579f47433b88e8d85291');
INSERT INTO `tech` VALUES (2, '柯南', '202000300312', '14e1b600b1fd579f47433b88e8d85291');
INSERT INTO `tech` VALUES (3, '思思', '20200300086', '14e1b600b1fd579f47433b88e8d85291');

-- ----------------------------
-- Table structure for time
-- ----------------------------
DROP TABLE IF EXISTS `time`;
CREATE TABLE `time`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of time
-- ----------------------------
INSERT INTO `time` VALUES (1, '2021/12/6', '2021/12/12');
INSERT INTO `time` VALUES (2, '2021/12/1', '2021/12/5');

SET FOREIGN_KEY_CHECKS = 1;
