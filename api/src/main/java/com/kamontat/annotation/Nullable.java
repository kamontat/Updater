package com.kamontat.annotation;

import java.lang.annotation.*;

/**
 * show that it can be {@code null}
 *
 * @author kamontat
 * @version 1.0
 * @since Mon 03/Apr/2017 - 12:10 AM
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Nullable {
}
