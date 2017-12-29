#!/usr/bin/env python
# -*- coding:utf-8 -*-

'''
 用于转换日期格式的工具类
'''

import re


class DateTransUtil(object):

    def __init__(self,):
        pass

    '''
    将日期字符串转换为 yyyy-MM-dd HH:mm:ss 的格式
    '''
    @staticmethod
    def trans_date_time_format(dateStr):
        # 将正则表达式编译成Pattern对象
        pattern = re.compile(r'(\d+)')
        match = pattern.findall(dateStr)
        length = len(match)
        # print match
        year = int(match[0])
        month = int(match[1])
        day = int(match[2])
        hour = int(match[3]) if length >= 4 else 0
        minute = int(match[4]) if length >= 5 else 0
        second = int(match[5]) if length >= 6 else 0

        month_str = str(month) if month >= 10 else '0' + str(month)
        day_str = str(day) if day >= 10 else '0' + str(day)
        hour_str = str(hour) if hour >= 10 else '0' + str(hour)
        minute_str = str(minute) if minute >= 10 else '0' + str(minute)
        second_str = str(second) if second >= 10 else '0' + str(second)

        return '' + str(year) + '-' + month_str + '-' + day_str + ' ' + hour_str + ':' + minute_str + ':' + second_str

    '''
    将年月类型的日期字符串转换为 yyyy/MM 的格式
    '''
    @staticmethod
    def trans_year_month_format(dateStr):
        # 将正则表达式编译成Pattern对象
        pattern = re.compile(r'(\d+)')
        match = pattern.findall(dateStr)
        # print match
        year = int(match[0])
        month = int(match[1])

        month_str = str(month) if month >= 10 else '0' + str(month)

        return '' + str(year) + '/' + month_str

    '''
    将日期字符串转换为 yyyy- MM - dd AM, yyyy - MM - dd PM 的格式
    '''
    @staticmethod
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

        month_str = str(month) if month > 10 else '0' + str(month)
        day_str = str(day) if day > 10 else '0' + str(day)

        return '' + str(year) + '-' + month_str + '-' + day_str + ' ' + period
