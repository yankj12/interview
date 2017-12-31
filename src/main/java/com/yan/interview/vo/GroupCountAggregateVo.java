package com.yan.interview.vo;

import java.io.Serializable;

import com.yan.access.vo.MenuVo;

public class GroupCountAggregateVo implements Serializable, Comparable<GroupCountAggregateVo>{

	private String id;
	
	private Integer count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int compareTo(GroupCountAggregateVo o) {
		return this.getId().compareTo(o.getId());
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + this.id + "\", \"count\":" + this.count + "}";
	}
	
}
