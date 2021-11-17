package mage;

import mage.filter.Filter;
import mage.filter.StaticFilters;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author TheElk801
 */
public class StaticFilterTest {

    @Test
    public void testStaticFilters() throws IllegalAccessException {
        for (Field field : StaticFilters.class.getFields()) {
            Assert.assertTrue("Field must be public: " + field, Modifier.isPublic(field.getModifiers()));
            Assert.assertTrue("Field must be static: " + field, Modifier.isStatic(field.getModifiers()));
            Assert.assertTrue("Field must be final: " + field, Modifier.isFinal(field.getModifiers()));
            Assert.assertTrue("Field name must start with \"FILTER_\"", field.getName().startsWith("FILTER_"));
            Assert.assertEquals("Field name must be all upper case letters", field.getName().toUpperCase(), field.getName());
            Object filter = field.get(field.getType());
            Assert.assertTrue("Field must be a filter: " + field, filter instanceof Filter);
            Assert.assertTrue("Field must be locked: " + field, ((Filter) filter).isLockedFilter());
        }
    }
}
