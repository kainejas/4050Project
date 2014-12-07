package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Item;

public class AuctionImpl extends Persistent implements Auction {
	
	private float minPrice;
	private Date expiration;
	private boolean isClosed;
	private float sellingPrice;
	private long itemId;
	
	public AuctionImpl(Item item, float minPrice, Date expiration) {
        if(item  != null)
		this.itemId = item.getId();
        else this.itemId = -1;
		this.minPrice = minPrice;
		this.expiration = expiration;
		this.sellingPrice = minPrice;
	}

	@Override
	public float getMinPrice() {
		return this.minPrice;
	}

	@Override
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;

	}

	@Override
	public Date getExpiration() {
		return this.expiration;
	}

	@Override
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	@Override
	public boolean getIsClosed() {
		return this.isClosed;
	}

	@Override
	public float getSellingPrice() {
		return this.sellingPrice;
	}
	
	@Override
	public void setSellingPrice(float price) {
		this.sellingPrice = price;
	}

	@Override
	public long getItemId() {
		return this.itemId;
	}

	@Override
	public void setItemId(long itemId) {
		this.itemId = itemId;

	}

}
