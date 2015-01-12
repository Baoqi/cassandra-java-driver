/*
    Copyright 2013, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.datastax.driver.mapper;

import com.datastax.driver.core.Session;
import com.sun.org.apache.bcel.internal.generic.Select;

/**
 * @author toddf
 * @since Aug 6, 2013
 */
public class DatastoreImpl
implements Datastore
{
	private Session session;
	private String dbName;
	
	public DatastoreImpl(Session session, String dbName)
	{
		super();
		this.session = session;
		this.dbName = dbName;
	}

	@Override
	public void createKeyspace()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void ensureIndexes()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void createSchema()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T save(T entity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void delete(T entity)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T read(Class<T> entityType, Object id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Select find(Class<T> entityType)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
