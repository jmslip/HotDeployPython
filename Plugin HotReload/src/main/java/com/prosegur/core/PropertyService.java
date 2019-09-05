package com.prosegur.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 *
 * @author Pongelupe
 */
public class PropertyService implements Serializable {

	private static final long serialVersionUID = 5507207935433372642L;

	private static final String BUNDLE_NAME = "com.prosegur.hot-reload";

	private static final String HOT_RELOAD_PROPERTIES = "./hot-reload.properties";

	private static PropertyService instance;

	private Properties properties;

	private PropertyService() {
		try {
			properties = new Properties();
			Bundle bundle = Platform.getBundle(BUNDLE_NAME);
			URL url = bundle.getEntry(HOT_RELOAD_PROPERTIES);
			File propertiesFile = new File(FileLocator.resolve(url).getFile());
			InputStream inputStream = new FileInputStream(propertiesFile);
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Error loading " + HOT_RELOAD_PROPERTIES + " file");
		}
	}

	public static PropertyService getInstance() {
		return Optional.ofNullable(instance).orElseGet(PropertyService::new);
	}

	public Optional<String> getProp(String propKey) {
		return Optional.ofNullable(this.properties.get(propKey))
				.map(Object::toString);
	}

}
