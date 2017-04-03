package com.kamontat.annotation;

import java.lang.annotation.*;

/**
 * For very long method, and should run in other thread
 *
 * @author kamontat
 * @version 1.0
 * @since Sun 02/Apr/2017 - 11:36 PM
 */
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface LongMethod {
}
