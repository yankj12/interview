#!/usr/bin/env python
# -*- coding:utf-8 -*-

'''
 修正interview表中 firstInterviewTimePeriod 一面面试时间段
 yyyy- MM - dd AM, yyyy - MM - dd PM
'''
__author__ = 'yankj12'

from pymongo import MongoClient
#from bson.objectid import ObjectId
from myutil import Properties

import re

# 读取properties的配置文件
dictProperties = Properties.Properties("db.properties").getProperties()
# print dictProperties

print '加载配置文件结束'

user = dictProperties['user']
pwd = dictProperties['password']
server = dictProperties['ip']
port = dictProperties['port']
db_name = dictProperties['dbUserDefined']

uri = 'mongodb://' + user + ':' + pwd + '@' + server + ':' + port + '/'+ db_name
client = MongoClient(uri)

db = client.manage


'''
将日期字符串转换为 yyyy- MM - dd AM, yyyy - MM - dd PM 的格式
'''
def trans_time_period(dateStr):
    # 将正则表达式编译成Pattern对象
    pattern = re.compile(r'(\d+)')
    match = pattern.findall(dateStr)
    # print match
    year = int(match[0])
    month = int(match[1])
    day = int(match[2])
    hour = int(match[3])

    period = 'AM' if hour <= 12 else 'PM'

    return '' + str(year) + '-' + (str(month) if month>10 else '0'+str(month)) + '-' + (str(day) if day>10 else '0'+str(day)) + ' ' + period


# 因为要处理所有的数据，所以要遍历所有的数据
interview_set = db.Interview
for i in interview_set.find({'firstInterviewTime':{ '$exists' : True }}):
    # print(i)
    if i['firstInterviewTime'] and i['firstInterviewTime'] != '':
        # 如果字段firstInterviewTime不为空的话，格式化时间，并更新字段firstInterviewTimePeriod
        # print i['firstInterviewTime']
        oId =  i['_id']
        time_period = trans_time_period(i['firstInterviewTime'])
        # print i['firstInterviewTime'], ', ', time_period

        # 更新字段firstInterviewTimePeriod
        interview_set.update({'_id': oId},{"$set":{'firstInterviewTimePeriod':time_period}})
    else:
        pass

print '数据修正结束'

