package com.flipkart.ecomsystem.dto;

import com.flipkart.ecomsystem.enums.OrderStatus;

public class OrderStatusDto {
   private OrderStatus status;

   public OrderStatus getStatus() {
	return status;
   }

   public void setStatus(OrderStatus status) {
	this.status = status;
   }
   
}
