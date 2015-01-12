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

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * @author toddf
 * @since Aug 6, 2013
 */
public class ColumnMapperTest
{
	@Test
	public void shouldUseAnnotationColumnName()
	throws SecurityException, NoSuchFieldException
	{
		Field field = TestEntity.class.getDeclaredField("funkenGruven");
		ColumnMapper<String> cm = new ColumnMapper<String>(field);
		assertEquals("shouldRename", cm.getColumnName());
		assertEquals(ByteBuffer.wrap("please rename this column".getBytes()), cm.toBytes("please rename this column"));
	}
}
