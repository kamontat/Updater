package com.kamontat.api;

import com.kamontat.constants.SizeUnit;
import com.kamontat.utilities.DesktopUtil;
import com.kamontat.utilities.FilesUtil;
import com.kamontat.utilities.SizeUtil;
import com.kamontat.utilities.URLManager;

import java.io.File;
import java.io.InputStream;
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
	/**
	 * open file
	 *
	 * @param fileNameAndPath
	 * 		path with file/folder name
	 */
	public static void open(String fileNameAndPath) {
		DesktopUtil.get().open(new File(fileNameAndPath));
	}
	
	/**
	 * remove current root file <br>
	 * <b>Be careful</b> to use this
	 */
	public static void removeThis() {
		FilesUtil.delFile(FilesUtil.getFileFromRoot().getAbsolutePath());
	}
	
	/**
	 * get callable that will load content in link and place in at dist directory
	 *
	 * @param link
	 * 		download link
	 * @param distDirectory
	 * 		destination directory
	 * @return callable when call will starting download
	 */
	@Deprecated
	public static Callable<String> getDownload(URL link, String distDirectory) {
		return () -> {
			URLManager manager = URLManager.getUrl(link);
			String fileName = manager.getURLFilename();
			if (!new File(distDirectory).isDirectory()) FilesUtil.createFolders(FilesUtil.separatePath(distDirectory));
			Path dist = Paths.get(distDirectory).resolve(fileName);
			if (dist.toFile().exists()) FilesUtil.delFile(dist.toFile().getAbsolutePath());
			try (InputStream stream = manager.getInputStream()) {
				byte[] buffer = new byte[SizeUtil.getSize(32L, SizeUnit.MB).convertTo(SizeUnit.B).getSizeInt()];
				int read = 0, result = 0;
				while ((read = stream.read(buffer)) != -1) {
					result += read;
					System.out.println(result);
				}
				Files.copy(stream, dist, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return dist.toFile().getAbsolutePath();
		};
	}
}
