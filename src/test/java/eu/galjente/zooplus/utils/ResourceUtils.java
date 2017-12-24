package eu.galjente.zooplus.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ResourceUtils {

	public static String readResourceFromClassPathAsString(String location) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(location);
		return inputStreamToString(classPathResource.getInputStream());
	}

	public static String readResourceAsString(ResourceLoader resourceLoader, String location) throws IOException {
		Resource resource = resourceLoader.getResource(location);
		return inputStreamToString(resource.getInputStream());
	}

	private static String inputStreamToString(InputStream is) throws IOException {
		try (Reader in = new InputStreamReader(is, "UTF-8")) {
			return FileCopyUtils.copyToString(in);
		}
	}
}
