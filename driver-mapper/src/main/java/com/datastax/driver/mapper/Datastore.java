package com.datastax.driver.mapper;

import com.sun.org.apache.bcel.internal.generic.Select;

public interface Datastore
{
	void createKeyspace();
	void ensureIndexes();
	public void createSchema();
	<T> T save(T entity);
	<T> void delete(T entity);
	<T> T read(Class<T> entityType, Object id);
	<T> Select find(Class<T> entityType);
}
