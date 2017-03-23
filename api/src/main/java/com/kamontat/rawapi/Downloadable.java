package com.kamontat.rawapi;

import com.kamontat.exception.UpdateException;

import java.util.concurrent.Callable;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:38 PM
 */
public interface Downloadable extends Callable<String> {
	String call() throws UpdateException;
}
