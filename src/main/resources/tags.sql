CREATE TABLE
    tag_position
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        tag_id VARCHAR(128) NOT NULL COMMENT 'TagID',
        tag_positionTS TIMESTAMP COMMENT '位置更新时间',
        tag_smoothedPostionX DECIMAL(14,2) COMMENT 'smoothedX位置',
        tag_smoothedPostionY DECIMAL(14,2) COMMENT 'smoothedY位置',
        tag_smoothedPostionZ DECIMAL(14,2) COMMENT 'smoothedZ位置',
        tag_smoothedPostionAccuracy DECIMAL(14,2) COMMENT 'smoothed位置精度',
        tag_postionX DECIMAL(14,2) COMMENT 'X位置',
        tag_postionY DECIMAL(14,2) COMMENT 'Y位置',
        tag_postionZ DECIMAL(14,2) COMMENT 'Z位置',
        tag_postionAccuracy DECIMAL(14,2) COMMENT '位置精度',
        tag_covarianceMatrixXX DECIMAL(14,2) COMMENT '协方差XX',
        tag_covarianceMatrixXY DECIMAL(14,2) COMMENT '协方差XY',
        tag_covarianceMatrixYX DECIMAL(14,2) COMMENT '协方差YX',
        tag_covarianceMatrixYY DECIMAL(14,2) COMMENT '协方差YY',
        PRIMARY KEY (id),
        CONSTRAINT uni_group_code UNIQUE (tag_id, tag_positionTS)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    tag_baseinfo
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        tag_id VARCHAR(128) NOT NULL COMMENT 'TagID',
        tag_name VARCHAR(128) COMMENT '名字',
        tag_color VARCHAR(128) COMMENT '颜色',
        tag_deviceType VARCHAR(128) COMMENT '设备类型',
        tag_group VARCHAR(128) COMMENT '分组',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    tag_zonesinfo
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        tag_id VARCHAR(128) NOT NULL COMMENT 'TagID',
        tag_positionTS TIMESTAMP NOT NULL COMMENT '位置更新时间',
        tag_zoneid VARCHAR(128) COMMENT 'zoneID',
        tag_zonename VARCHAR(128) COMMENT 'zone名字',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
    tag_areainfo
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        tag_id VARCHAR(128) NOT NULL COMMENT 'TagID',
        tag_positionTS TIMESTAMP NOT NULL COMMENT '位置更新时间',
        tag_coordinateSystemid VARCHAR(128) COMMENT 'coordinateSystemID',
        tag_coordinateSystemname VARCHAR(128) COMMENT 'coordinateSystem名字',
        tag_areaid VARCHAR(128) COMMENT 'areaID',
        tag_areaname VARCHAR(128) COMMENT 'area名字',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET SQL_MODE = 'ALLOW_INVALID_DATES';
CREATE TABLE
    tag_statusinfo
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
        tag_id VARCHAR(128) NOT NULL COMMENT 'TagID',
        tag_lastpacketTS TIMESTAMP COMMENT '最后包时间',
        tag_batteryVoltage DECIMAL(14,2) COMMENT '电量',
        tag_batteryVoltageTS TIMESTAMP COMMENT '电量更新时间',
        tag_batteryAlarm VARCHAR(128) COMMENT '电量报警',
        tag_batteryAlarmTS TIMESTAMP COMMENT '电量报警时间',
        tag_buttonState VARCHAR(128) COMMENT '按钮状态',
        tag_buttonStateTS TIMESTAMP COMMENT '按钮状态更新时间',
        tag_lastButtonPressTS TIMESTAMP COMMENT '按钮按压时间',
        tag_lastButton2PressTS TIMESTAMP COMMENT '按钮2按压时间',
        tag_rssi DECIMAL(14,2) COMMENT '信号强度',
        tag_rssiTS TIMESTAMP COMMENT '信号强度更新时间',
        tag_rssiLocator VARCHAR(128) COMMENT '基站ID',
        tag_txrate DECIMAL(14,2) COMMENT '信号速率',
        tag_txrateTS TIMESTAMP COMMENT '信号速率更新时间',
        tag_txpower DECIMAL(14,2) COMMENT '信号能量',
        tag_txpowerTS TIMESTAMP COMMENT '信号能量更新时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
INSERT INTO tag_baseinfo (tag_id, tag_name, tag_color, tag_deviceType, tag_group) VALUES ('ff344a2b7fcd6892', '张三', 'red', 'tag', '3');
INSERT INTO tag_baseinfo (tag_id, tag_name, tag_color, tag_deviceType, tag_group) VALUES ('ff344a2b7fcd6893', '李四', 'blue', 'tag', '1');
INSERT INTO tag_baseinfo (tag_id, tag_name, tag_color, tag_deviceType, tag_group) VALUES ('ff344a2b7fcd6894', '王五', 'blue', 'tag', '1');
INSERT INTO tag_baseinfo (tag_id, tag_name, tag_color, tag_deviceType, tag_group) VALUES ('ff344a2b7fcd6895', '赵柳', 'white', 'tag', '2');
INSERT INTO tag_baseinfo (tag_id, tag_name, tag_color, tag_deviceType, tag_group) VALUES ('ff344a2b7fcd6896', '钱一', 'white', 'tag', '2');
