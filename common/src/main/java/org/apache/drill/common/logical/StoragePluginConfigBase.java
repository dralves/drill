/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.common.logical;

import java.util.List;

import org.apache.drill.common.config.CommonConstants;
import org.apache.drill.common.config.DrillConfig;
import org.apache.drill.common.util.PathScanner;

import com.google.common.base.Joiner;


public abstract class StoragePluginConfigBase extends StoragePluginConfig {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StoragePluginConfigBase.class);


  public synchronized static Class<?>[] getSubTypes(final DrillConfig config) {
    final List<String> packages =
        config.getStringList(CommonConstants.STORAGE_PLUGIN_CONFIG_SCAN_PACKAGES);
    final Class<?>[] pluginClasses =
        PathScanner.scanForImplementationsArr(StoragePluginConfig.class, packages);
    final String lineBrokenList =
        pluginClasses.length == 0
        ? "" : "\n\t- " + Joiner.on("\n\t- ").join(pluginClasses);
    logger.debug("Found {} storage plugin configuration classes: {}.",
                 pluginClasses.length, lineBrokenList);
    return pluginClasses;
  }

  @Override
  public abstract boolean equals(Object o);

}
