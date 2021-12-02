package mage;

import mage.filter.Filter;
import mage.filter.StaticFilters;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author TheElk801
 */
public class StaticFilterTest {

    @Test
    public void testStaticFilters() throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        for (Field field : StaticFilters.class.getFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                errors.add("Field must be public: " + field);
            }
            if (!Modifier.isStatic(field.getModifiers())) {
                errors.add("Field must be static: " + field);
            }
            if (!Modifier.isFinal(field.getModifiers())) {
                errors.add("Field must be final: " + field);
            }
            if (!field.getName().startsWith("FILTER_")) {
                errors.add("Field name must start with \"FILTER_\"");
            }
            if (!Objects.equals(field.getName().toUpperCase(), field.getName())) {
                errors.add("Field name must be all upper case letters");
            }
            Object filter = field.get(field.getType());
            if (!(filter instanceof Filter)) {
                errors.add("Field must be a filter: " + field);
            } else if (!((Filter) filter).isLockedFilter()) {
                errors.add("Field must be locked: " + field);
            }
        }
        if (errors.size() > 0) {
            errors.stream().forEach(System.out::println);
        }
        Assert.assertFalse("There were errors found in Static Filters", errors.size() > 0);
    }
}
