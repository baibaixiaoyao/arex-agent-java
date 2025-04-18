package io.arex.inst.httpservlet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ServletUtilTest {
    @Test
    void appendUri() {
        assertEquals("http://arextest.com?name=mark", ServletUtil.appendUri("http://arextest.com", "name", "mark"));
        assertEquals("http://arextest.com?name=mark#fragment",
            ServletUtil.appendUri("http://arextest.com#fragment", "name", "mark"));

        assertEquals("http://arextest.com?email=arex.test.com@gmail.com&name=mark",
            ServletUtil.appendUri("http://arextest.com?email=arex.test.com@gmail.com", "name", "mark"));

        assertEquals("http://arextest.com?email=arex.test.com@gmail.com&name=mark#fragment",
            ServletUtil.appendUri("http://arextest.com?email=arex.test.com@gmail.com#fragment", "name", "mark"));

        assertEquals("http://arextest.com?Signature=HJeNHsZ7%2BDMj0JsTK3zd3nzBDQE%3D&name=mark",
            ServletUtil.appendUri("http://arextest.com?Signature=HJeNHsZ7%2BDMj0JsTK3zd3nzBDQE%3D", "name", "mark"));
    }

    @Test
    public void getFullPath() {
        assertEquals("/servletpath/controll/action", ServletUtil.getRequestPath("http://arextest.com/servletpath/controll/action"));
        assertEquals("/servletpath/controll/action?k1=v1", ServletUtil.getRequestPath("http://arextest.com/servletpath/controll/action?k1=v1"));
    }

    @Test
    public void matchRequestParams() {
        Map<String, List<String>> requestParams = new HashMap<>();
        requestParams.put("name", new ArrayList<>(Arrays.asList("kimi", null)));
        requestParams.put("age", new ArrayList<>(Arrays.asList("0")));
        assertFalse(ServletUtil.matchAndRemoveRequestParams(requestParams, "name", "lock"));

        //requestParams has null value,targetValue is not null
        assertTrue(ServletUtil.matchAndRemoveRequestParams(requestParams, "age", "0"));

        //test: requestParams has null value, and targetValue is null
        assertTrue(ServletUtil.matchAndRemoveRequestParams(requestParams, "name", null));

        assertFalse(ServletUtil.matchAndRemoveRequestParams(Collections.emptyMap(), "name", "lock"));
    }

    @Test
    public void getRequestParams() {
        String queryString = "name=kimi&age=0";
        Map<String, List<String>> requestParams = ServletUtil.getRequestParams(queryString);
        assertEquals(2, requestParams.size());
    }
}
