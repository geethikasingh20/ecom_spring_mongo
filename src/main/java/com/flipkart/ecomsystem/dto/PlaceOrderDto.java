package com.flipkart.ecomsystem.dto;

import java.util.List;

import com.flipkart.ecomsystem.entity.OrderItems;

public class PlaceOrderDto {
	private String userId;
    private List<OrderItems> items;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<OrderItems> getItems() {
		return items;
	}
	public void setItems(List<OrderItems> items) {
		this.items = items;
	}
    
    
    
}
