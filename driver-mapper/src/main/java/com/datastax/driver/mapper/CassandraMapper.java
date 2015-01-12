package com.datastax.driver.mapper;

import java.util.HashMap;
import java.util.Map;

import com.datastax.driver.core.Session;

/**
 * CassanrdaMapper is an object containing a Cassandra Session (connections to a cluster) and
 * mappings for entities.
 * </p>
 * Usage:
 * </p>
 * Session session = // create and configure a Cassandra session...
 * CassandraMapper mapper = new CassandraMapper(session);
 * mapper.map(Person.class);
 * mapper.map(Users.class);
 * mapper.map(Managers.class, Peons.class);
 * Datastore d = mapper.getDatastore("mykeyspace");
 * d.createSchema();
 * d.ensureIndexes();
 * Person p = d.find(Person.class).query("");
 * 
 * @author toddf
 * @since Dec 7, 2012
 */
public class CassandraMapper
{
	public static final String IGNORED_FIELD = "-1";

	private Session session;
	private Map<String, EntityMapper<?>> mappedClasses = new HashMap<String, EntityMapper<?>>();

	/**
	 * Create a CassandraMapper for the given pre-configured Session.
	 * 
	 * @param session a Cassandra session.
	 */
	public CassandraMapper(Session session)
	{
		super();
		this.session = session;
	}

	/**
	 * Determine if an Entity is already mapped.
	 * 
	 * @param entityClass
	 * @return true if already mapped. Otherwise, false.
	 */
	public boolean isMapped(Class<?> entityClass)
	{
		return mappedClasses.containsKey(entityClass.getCanonicalName());
	}

	/**
	 * Map one or more entities.  If any of the entities are already mapped,
	 * they are not mapped again.
	 * 
	 * @param entityClasses
	 * @return
	 */
	public <T> CassandraMapper map(Class<T>... entityClasses)
	{
		if (entityClasses != null && entityClasses.length > 0)
		{
			for (Class<T> entityClass : entityClasses)
			{
				if (!isMapped(entityClass))
				{
					map(entityClass, new EntityMapper<T>(entityClass));
				}
			}
		}

		return this;
	}

	/**
	 * Map an entity with the given EntityMapper.  If entity is already mapped, 
	 * call is ignored.
	 * 
	 * @param entityClass
	 * @param mapper
	 * @return
	 */
	public <T> CassandraMapper map(Class<T> entityClass, EntityMapper<T> mapper)
	{
		if (!isMapped(entityClass))
		{
			mappedClasses.put(entityClass.getCanonicalName(), mapper);
		}

		return this;
	}

	public Datastore createDatastore(String dbName)
    {
	    return new DatastoreImpl(session, dbName);
    }
}
