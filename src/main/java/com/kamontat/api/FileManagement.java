package com.kamontat.api;

import com.kamontat.utilities.DesktopUtil;
import com.kamontat.utilities.FilesUtil;
import com.kamontat.utilities.URLsUtil;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:17 AM
 */
public class FileManagement {
	public static void open(String fileNameAndPath) {
		DesktopUtil.get().open(new File(fileNameAndPath));
	}
	
	public static void removeThis() {
		FilesUtil.delFile(FilesUtil.getFileFromRoot().getAbsolutePath());
	}
	
	public static Callable<String> getDownload(URL link, String distDirectory) {
		return () -> {
			String fileName = URLsUtil.getUrl(link).getURLFilename();
			Path dist = Paths.get(distDirectory).resolve(fileName);
			
			Files.copy(link.openStream(), dist, StandardCopyOption.REPLACE_EXISTING);
			return dist.toFile().getAbsolutePath();
		};
	}
}
