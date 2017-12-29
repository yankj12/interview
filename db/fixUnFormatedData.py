#!/usr/bin/env python
# -*- coding:utf-8 -*-

'''
 转换以下字段的格式
 birth yyyy/MM
 graduateMonth yyyy/MM
 firstJobBeginMonth yyyy/MM
 firstPhoneCallTime yyyy-MM-dd HH:mm:ss
 firstInterviewTime yyyy-MM-dd HH:mm:ss
 secondInterviewTime yyyy-MM-dd HH:mm:ss
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


'''
格式化字段 yyyy/MM
'''

def format_year_month(interview_set, field):
    for i in interview_set.find({field: {'$exists': True}}):
        # print(i)
        if i[field] and i[field] != '':
            o_id = i['_id']
            format_str = DateTransUtil.trans_year_month_format(i[field])
            interview_set.update({'_id': o_id}, {"$set": {field: format_str}})


'''
格式化字段 yyyy - MM - dd HH:mm:ss
'''

def format_date_time(interview_set, field):
    for i in interview_set.find({field: {'$exists': True}}):
        # print(i)
        if i[field] and i[field] != '':
            o_id = i['_id']
            format_str = DateTransUtil.trans_date_time_format(i[field])
            interview_set.update({'_id': o_id}, {"$set": {field: format_str}})


# 格式化 birth yyyy/MM
format_year_month(interview_set, 'birth')

# 格式化 graduateMonth yyyy/MM
format_year_month(interview_set, 'graduateMonth')

# 格式化 firstJobBeginMonth yyyy/MM
format_year_month(interview_set, 'firstJobBeginMonth')

format_date_time(interview_set, 'firstPhoneCallTime')
format_date_time(interview_set, 'firstInterviewTime')
format_date_time(interview_set, 'secondInterviewTime')

print '数据修正结束'

