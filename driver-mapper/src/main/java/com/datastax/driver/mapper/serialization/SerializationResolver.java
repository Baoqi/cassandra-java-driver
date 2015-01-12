package com.datastax.driver.mapper.serialization;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.BooleanType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.DateType;
import org.apache.cassandra.db.marshal.DecimalType;
import org.apache.cassandra.db.marshal.DoubleType;
import org.apache.cassandra.db.marshal.FloatType;
import org.apache.cassandra.db.marshal.Int32Type;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.UUIDType;

/**
 * A utility class to determine which Serializer to use based on a type.
 * 
 * @author toddf
 * @since Apr 16, 2013
 */
public class SerializationResolver
{
	private static final Map<Class<?>, AbstractType<?>> SERIALIZERS = new HashMap<Class<?>, AbstractType<?>>();

	static
	{
		SERIALIZERS.put(UUID.class, UUIDType.instance);
		SERIALIZERS.put(String.class, AsciiType.instance);
		SERIALIZERS.put(Long.class, LongType.instance);
		SERIALIZERS.put(long.class, LongType.instance);
		SERIALIZERS.put(Integer.class, Int32Type.instance);
		SERIALIZERS.put(int.class, Int32Type.instance);
//		SERIALIZERS.put(Short.class, Int32Type.instance);
//		SERIALIZERS.put(short.class, Int32Type.instance);
		SERIALIZERS.put(Byte.class, BytesType.instance);
		SERIALIZERS.put(byte.class, BytesType.instance);
		SERIALIZERS.put(Float.class, FloatType.instance);
		SERIALIZERS.put(float.class, FloatType.instance);
		SERIALIZERS.put(Double.class, DoubleType.instance);
		SERIALIZERS.put(double.class, DoubleType.instance);
		SERIALIZERS.put(BigInteger.class, IntegerType.instance);
		SERIALIZERS.put(BigDecimal.class, DecimalType.instance);
		SERIALIZERS.put(Boolean.class, BooleanType.instance);
		SERIALIZERS.put(boolean.class, BooleanType.instance);
//		SERIALIZERS.put(byte[].class, BytesArraySerializer.get());
		SERIALIZERS.put(ByteBuffer.class, BytesType.instance);
		SERIALIZERS.put(Date.class, DateType.instance);
	}

	/**
	 * Augment the Serializer mapping to resolve different or additional serializers.
	 * This method modifies the singleton, static Map of Class<?> to Serializer<?>.
	 * 
	 * @param serializer the Serializer to use
	 * @param serializedClasses the types with which to associate to this Serializer. 
	 */
	public static void setSerializer(AbstractType<?> serializer, Class<?>... serializedClasses)
	{
		if (serializedClasses == null) return;
		
		for (Class<?> serialized : serializedClasses)
		{
			SERIALIZERS.put(serialized, serializer);
		}
	}

    public static AbstractType<?> resolve(Object value)
	{
		if (value == null)
		{
			return BytesType.instance;
		}
		else
		{
			return resolve(value.getClass());
		}
	}

    public static AbstractType<?> resolve(Class<?> valueClass)
	{
		if (valueClass == null)
		{
			return BytesType.instance;
		}
		else
		{
			AbstractType<?> s = SERIALIZERS.get(valueClass);
			
			if (s == null)
			{
				return BytesType.instance;
			}

			return s;
		}
	}
}
