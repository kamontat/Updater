package com.kamontat.example;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.implementation.GUpdater;
import com.kamontat.object.Owner;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.MultiThread;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:54 AM
 */
public class TestImp {
	public static void main(String[] args) throws UpdateException {
		Updater update = new GUpdater(new Owner("kamontat", "CheckIDNumber"), "v1.0.0", 1);
		
		MultiThread t = new MultiThread(Executors.newFixedThreadPool(2));
		
		List<Future<Updatable>> list = t.executeAndWaitAll(update);
		try {
			t.shutdown();
			System.out.println(list.get(0).get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		MultiThread t2 = new MultiThread(Executors.newFixedThreadPool(2));
		List<Future<String>> newList = t.executeAndWaitAll(update.getDownload());
		try {
			t.shutdown();
			System.out.println(list.get(0).get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
