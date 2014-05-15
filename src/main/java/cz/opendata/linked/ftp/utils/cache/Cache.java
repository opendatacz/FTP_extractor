package cz.opendata.linked.ftp.utils.cache;

import java.io.*;

import org.w3c.dom.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *  Document cache. It stores downloaded files to hard drive.
 *
 *
 */
public class Cache extends cz.opendata.linked.ftp.utils.Object {

	private static Cache instance = null;

	protected Cache() {
		// defeat instantiation.
	}

	public static Cache getInstance() {
		if(instance == null) {
			instance = new Cache();
		}
		return instance;
	}

	/**
	 * Dir where downloaded documents is stored
	 */
	private String cacheDir =  "Cache";

	/**
	 * Path where cache dir is located
	 */
	private String basePath = "./";


	/**
	 * Dir location where downloaded files will be cached
	 * @param cacheDir
	 */
	public void setCacheDir(String cacheDir) {
		this.cacheDir = cacheDir;
	}

	/**
	 * Path where cache dir is located
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = (basePath.endsWith("/") ? basePath.substring(0, basePath.length()-1) : basePath);
	}

	/**
	 *
	 * @return cache dir location
	 */
	public File getCacheDir() {

		return new File(this.basePath,this.cacheDir);

	}

	/**
	 * Check if is document already cached
	 * @param filePath
	 */
	public boolean isCached(String filePath) {

		File file = new File(this.getCacheDir(), filePath);

		return file.exists();
	}

	/**
	 * @param filePath
	 * @return cached file by file path
	 */
	public File getFile(String filePath) {

		File file = null;

		if(this.isCached(filePath)) {
			file = new File(this.getCacheDir(), filePath);
		}

		return file;
	}


}