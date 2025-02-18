package com.zoho.bean;

import java.util.Date;

public class CustomerOrder {
	private int orderId;
	private Date orderDate;
	private String userId;
	private String status;
	private int paymentId;

	public CustomerOrder(int orderId, Date orderDate, String userId, String status, int paymentId) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.userId = userId;
		this.status = status;
		this.paymentId = paymentId;
	}

	public CustomerOrder() {
		// TODO Auto-generated constructor stub
	}


	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public String toString() {
		return "CustomerOrder [orderId=" + orderId + ", orderDate=" + orderDate + ", userId=" + userId + ", status="
				+ status + ", paymentId=" + paymentId + "]";
	}

}
