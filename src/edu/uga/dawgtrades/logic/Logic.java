package edu.uga.dawgtrades.logic;

import java.util.List;

import edu.uga.dawgtrades.model.*;

public interface Logic {
	public long defineCategory(String categoryName, long parentId) throws DTException;
}