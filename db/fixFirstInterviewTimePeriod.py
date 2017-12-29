#!/usr/bin/env python
# -*- coding:utf-8 -*-

'''
 修正interview表中 firstInterviewTimePeriod 一面面试时间段
 yyyy- MM - dd AM, yyyy - MM - dd PM
'''
__author__ = 'yankj12'

from pymongo import MongoClient
#from bson.objectid import ObjectId
from myutil.Properties import Properties
from myutil.DateTransUtil import DateTransUtil
import urllib
import re

# 读取properties的配置文件
dictProperties = Properties("db.properties").getProperties()
# print dictProperties

print '加载配置文件结束'

user = dictProperties['user']
pwd = dictProperties['password']
server = dictProperties['ip']
port = dictProperties['port']
db_name = dictProperties['dbUserDefined']

user = urllib.quote_plus(user)
pwd = urllib.quote_plus(pwd)
uri = 'mongodb://' + user + ':' + pwd + '@' + server + ':' + port + '/'+ db_name
client = MongoClient(uri)

db = client.manage

# 因为要处理所有的数据，所以要遍历所有的数据
interview_set = db.Interview
for i in interview_set.find({'firstInterviewTime':{ '$exists' : True }}):
    # print(i)
    if i['firstInterviewTime'] and i['firstInterviewTime'] != '':
        # 如果字段firstInterviewTime不为空的话，格式化时间，并更新字段firstInterviewTimePeriod
        # print i['firstInterviewTime']
        oId =  i['_id']
        time_period = DateTransUtil.trans_time_period(i['firstInterviewTime'])
        # print i['firstInterviewTime'], ', ', time_period

        # 更新字段firstInterviewTimePeriod
        interview_set.update({'_id': oId},{"$set":{'firstInterviewTimePeriod':time_period}})
    else:
        pass

print '数据修正结束'

