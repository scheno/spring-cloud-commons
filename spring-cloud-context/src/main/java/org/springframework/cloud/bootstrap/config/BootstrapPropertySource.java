/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.bootstrap.config;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import static org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME;

/**
 * @author Ryan Baxter
 */
public class BootstrapPropertySource<T> extends EnumerablePropertySource<T> {

	private PropertySource<T> delegate;

	public BootstrapPropertySource(PropertySource<T> delegate) {
		super(BOOTSTRAP_PROPERTY_SOURCE_NAME + "-" + delegate.getName(),
				delegate.getSource());
		this.delegate = delegate;
	}

	@Override
	public Object getProperty(String name) {
		return this.delegate.getProperty(name);
	}

	@Override
	public String[] getPropertyNames() {
		Set<String> names = new LinkedHashSet<>();
		if (!(this.delegate instanceof EnumerablePropertySource)) {
			throw new IllegalStateException(
					"Failed to enumerate property names due to non-enumerable property source: "
							+ this.delegate);
		}
		names.addAll(Arrays.asList(
				((EnumerablePropertySource<?>) this.delegate).getPropertyNames()));

		return StringUtils.toStringArray(names);
	}

	public PropertySource<T> getDelegate() {
		return delegate;
	}

}
