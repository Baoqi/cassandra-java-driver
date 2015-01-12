package com.datastax.driver.mapper;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.datastax.driver.mapper.annotations.Column;
import com.datastax.driver.mapper.annotations.Entity;
import com.datastax.driver.mapper.annotations.NotSaved;
import com.datastax.driver.mapper.utils.ClassUtils;

/**
 * An EntityMapper is responsible for mapping a single type to Cassandra.
 * An entity with the @Entity("keyspacename") annotation is set will use the
 * key space name given in the annotation.  Otherwise, the key space name
 * will be the same as the simple class name. 
 * </p>
 * All properties (including those in super-classes) will be mapped as individual
 * columns by default, unless 'transient', 'static', 'final' or annotated with @NotSaved.
 * </p>
 * Properties annotated with @Column are mapped even if they are of the afore mentioned
 * ignored types.
 *  
 * @author toddf
 * @since Aug 6, 2013
 */
public class EntityMapper<T>
{
	private String columnFamily;
	private Map<String, ColumnMapper<?>> columns = new ConcurrentHashMap<String, ColumnMapper<?>>();
//	private boolean shouldStoreClassName = false;

	public EntityMapper(Class<T> entityType)
	{
		super();
		setColumnFamily(determineColumnFamily(entityType));
		mapColumns(entityType);
	}

	private void mapColumns(Class<T> entityType)
    {
		Collection<Field> fields = ClassUtils.getAllDeclaredFields(entityType);
		
		for (Field field : fields)
		{
			if (!isIgnored(field) || field.isAnnotationPresent(Column.class))
			{
				map(field);
			}
		}
    }

	public EntityMapper<T> setColumnFamily(String name)
	{
		this.columnFamily = name;
		return this;
	}

	public void map(ColumnMapper<?> columnMapper)
    {
		if (!isMapped(columnMapper.getColumnName()))
		{
			columns.put(columnMapper.getColumnName(), columnMapper);
		}
    }

	public boolean isMapped(String columnName)
    {
	    return columns.containsKey(columnName);
    }

	public T compose(ByteBuffer bytes)
	{
		// TODO method stub compose()
		return null;
	}
	
	public ByteBuffer decompose(T entity)
	{
		// TODO method stub decompose()
		return null;
	}

	private void map(Field field)
    {
		map(new ColumnMapper(field));
    }

	private boolean isIgnored(Field field)
    {
		if (field.isAnnotationPresent(NotSaved.class))
		{
			return true;
		}

	    return false;
    }

	private String determineColumnFamily(Class<?> entityType)
    {
		if (entityType == null) return null;

		if (entityType.isAnnotationPresent(Entity.class))
		{
			return entityType.getAnnotation(Entity.class).value();
		}
		
		return entityType.getClass().getSimpleName();
    }
}
