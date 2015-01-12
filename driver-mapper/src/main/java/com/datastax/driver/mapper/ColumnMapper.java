package com.datastax.driver.mapper;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.apache.cassandra.db.marshal.AbstractType;

import com.datastax.driver.mapper.annotations.Column;
import com.datastax.driver.mapper.serialization.SerializationResolver;

/**
 * A ColumnMapper is simply a mapping from a column name to a converter, which is able to 
 * convert a ByteBuffer into an Object (such as the contents of a field) and visa versa.
 * 
 * @author toddf
 * @since Aug 6, 2013
 */
public class ColumnMapper<T>
{
	private String columnName;
	private AbstractType<T> converter;

	public ColumnMapper(Field field)
    {
		super();
		initialize(field);
    }

	@SuppressWarnings("unchecked")
    private void initialize(Field field)
    {
		if (field.isAnnotationPresent(Column.class))
		{
			Column c = field.getAnnotation(Column.class);
			
			if (c.converter() != null)
			{
                setConverter((AbstractType<T>) SerializationResolver.resolve(c.converter()));
			}
			else
			{
				setConverter((AbstractType<T>) SerializationResolver.resolve(field.getType()));
			}

			if (c.value() != CassandraMapper.IGNORED_FIELD)
			{
				setColumnName(c.value());
			}
			else
			{
				setColumnName(field.getName());
			}
		}
		else
		{
			setConverter((AbstractType<T>) SerializationResolver.resolve(field.getType()));
			setColumnName(field.getName());
		}
    }

	public ColumnMapper<T> setColumnName(String name)
	{
		this.columnName = name;
		return this;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public ColumnMapper<T> setConverter(AbstractType<T> converter)
	{
		this.converter = converter;
		return this;
	}

	public ByteBuffer toBytes(T value)
	{
		return converter.decompose(value);
	}

	public Object fromBytes(ByteBuffer bytes)
	{
		return converter.compose(bytes);
	}
}
