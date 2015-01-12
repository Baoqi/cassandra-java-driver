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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import org.apache.cassandra.utils.ByteBufferUtil;

import com.datastax.driver.mapper.annotations.Column;
import com.datastax.driver.mapper.annotations.Entity;
import com.datastax.driver.mapper.annotations.NotSaved;

/**
 * @author toddf
 * @since Aug 6, 2013
 */
@Entity("excellence")
public class TestEntity
{
	// static fields are not mapped by default.
	private static String A_STATIC = "not mapped";

	// transient fields are not mapped by default.
	private transient int transientInt = 42;
	
	// final fields are not mapped by default. 
	private final int finalInt = 57;

	@NotSaved
	private String notSaved = "annotated with @NotSaved";
	
	// ignored fields annotated with @Column are mapped.
	@Column
	private static String staticString = "annotated with @Column";
	
	@Column
	private final String finalString = "annotated with @Column";
	
	@Column
	private transient String transientString = "annotated with @Column";
	
	// non-ignored types are mapped by default.
	private boolean isBoolean = true;
	private Boolean isBooleanObject = Boolean.TRUE;
	private long longValue = 34543433L;
	private Long longObjectValue = 7654345656L;
	private int intValue = 37;
	private Integer integerValue = 88;
	private double doubleValue = 3.14159;
	private Double doubleObjectValue = 9.8696;
	private BigInteger bigInteger = new BigInteger("65536");
	private BigDecimal bigDecimal = new BigDecimal("1234567890");
	private Date dateValue = new Date();
	private UUID uuid = UUID.randomUUID();
	private ByteBuffer byteBuffer = ByteBufferUtil.bytes("here is a byte buffer", Charset.forName("UTF-8"));
	
	@Column("shouldRename")
	private String funkenGruven = "please rename this column";
	
	@Override
	public boolean equals(Object that)
	{
		return equals((TestEntity) that);
	}
	
	public boolean equals(TestEntity that)
	{
		return true;
	}
}
