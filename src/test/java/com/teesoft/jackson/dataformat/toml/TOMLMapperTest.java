package com.teesoft.jackson.dataformat.toml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TOMLMapperTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TOMLMapperTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TOMLMapperTest.class);
    }

    /**
     * Test toml
     */
    public void testToml() throws JsonProcessingException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        TOMLMapper mapper = new TOMLMapper();
        MyValue value = jsonMapper.readValue("{\"name\":\"Bob\", \"age\":13,\"subvalue\":{\"name\":\"justdb\", \"age\":1}}", MyValue.class);
        String toml = mapper.writeValueAsString(value);
        System.out.println(toml);
        assertEquals(toml,
                "name = \"Bob\"\n"
                + "age = 13\n"
                + "\n"
                + "[subvalue]\n"
                + "name = \"justdb\"\n"
                + "age = 1\n");
        MyValue valueNew = mapper.readValue(toml, MyValue.class);
        String toml2 = mapper.writeValueAsString(valueNew);
        assertEquals(toml, toml2);
        assertEquals(value.name, "Bob");
        assertEquals(value.age, 13);
        assertEquals(valueNew.name, "Bob");
        assertEquals(valueNew.age, 13);
        mapper.writeValue(new File("target/test.toml"), value);
        MyValue valueFile = mapper.readValue(new File("target/test.toml"), MyValue.class);
        String toml3 = mapper.writeValueAsString(valueFile);
        assertEquals(toml, toml3);

    }

    public static class MyValue {

        public String name;
        public int age;
        public SubValue subvalue;
    }

    public static class SubValue {

        public String name;
        public int age;
    }
}