package com.datastax.driver.mapper.serialization;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.MarshalException;
import org.apache.cassandra.utils.ByteBufferUtil;

/**
 * A UUIDSerializer very efficiently translates a UUID byte[] to and from UUID
 * types.
 * 
 * @author toddf
 * @since April 16, 2013
 */
public class UUIDSerializer
extends AbstractType<UUID>
{

	public static final UUIDSerializer instance = new UUIDSerializer();

	@Override
    public int compare(ByteBuffer first, ByteBuffer second)
    {
	    // TODO Auto-generated method stub
	    return 0;
    }

	@Override
    public UUID compose(ByteBuffer bytes)
    {
		if (bytes == null)
		{
			return null;
		}

		return new UUID(bytes.getLong(), bytes.getLong());
    }

	@Override
    public ByteBuffer decompose(UUID uuid)
    {
		if (uuid == null)
		{
			return null;
		}

		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb;
    }

	@Override
    public ByteBuffer fromString(String string)
    throws MarshalException
    {
		if (string == null || string.trim().isEmpty())
		{
			return ByteBufferUtil.EMPTY_BYTE_BUFFER;
		}

		try
		{
			return decompose(UUID.fromString(string));
		}
		catch(IllegalArgumentException e)
		{
			throw new MarshalException("Invalid UUID string", e);
		}
    }

	@Override
    public String getString(ByteBuffer bytes)
    {
		return compose(bytes).toString();
    }

	@Override
    public void validate(ByteBuffer bytes)
    throws MarshalException
    {
        if ((bytes.remaining() != 0) && (bytes.remaining() != 16))
        {
            throw new MarshalException("UUIDs must be exactly 16 bytes");
        }
    }
}
