package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Persistable;

public class Persistent implements Persistable {
	
	private long id;
	public Persistent(){
		this.id = -1;
	}
	
	public Persistent(long id)
	{
		this.id = id;
	}
	
	
	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
		
	}

	@Override
	public boolean isPersistent() {
		return id >= 0;
	}

}
