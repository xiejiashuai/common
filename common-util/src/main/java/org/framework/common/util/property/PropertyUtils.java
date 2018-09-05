package org.framework.common.util.property;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public final class PropertyUtils {

    private PropertyUtils() {
        throw new RuntimeException("can not be constructor");
    }

    /**
     * eg
     *       propertySources={[spring.application.name,nihao],[spring.application.test=foo]} prefix=spring.application
     *       return {[name,nihao],[test=foo]}
     * @param propertySources {@link PropertySource} Iterable
     * @param prefix          the prefix of property name
     * @return Map
     * @see Properties
     */
    public static Map<String, Object> getSubProperties(Iterable<PropertySource<?>> propertySources, String prefix) {

        Map<String, Object> subProperties = new LinkedHashMap<String, Object>();

        String normalizedPrefix = normalizePrefix(prefix);

        for (PropertySource<?> source : propertySources) {
            if (source instanceof EnumerablePropertySource) {
                for (String name : ((EnumerablePropertySource<?>) source).getPropertyNames()) {
                    if (!subProperties.containsKey(name) && name.startsWith(normalizedPrefix)) {
                        String subName = name.substring(normalizedPrefix.length());
                        if (!subProperties.containsKey(subName)) { // take first one
                            Object value = source.getProperty(name);
                            subProperties.put(subName, value);
                        }
                    }
                }
            }
        }

        return subProperties;

    }

    /**
     * Normalize the prefix
     *
     * @param prefix the prefix
     * @return the prefix
     */
    public static String normalizePrefix(String prefix) {
        return prefix.endsWith(".") ? prefix : prefix + ".";
    }


}
