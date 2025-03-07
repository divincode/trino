/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.filesystem.manager;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.airlift.configuration.testing.ConfigAssertions.assertFullMapping;
import static io.airlift.configuration.testing.ConfigAssertions.assertRecordedDefaults;
import static io.airlift.configuration.testing.ConfigAssertions.recordDefaults;
import static io.trino.filesystem.manager.FileSystemConfig.CacheType.ALLUXIO;
import static io.trino.filesystem.manager.FileSystemConfig.CacheType.NONE;

public class TestFileSystemConfig
{
    @Test
    public void testDefaults()
    {
        assertRecordedDefaults(recordDefaults(FileSystemConfig.class)
                .setHadoopEnabled(true)
                .setNativeAzureEnabled(false)
                .setNativeS3Enabled(false)
                .setNativeGcsEnabled(false)
                .setNativeS3ExpressEnabled(true)
                .setCacheType(NONE));
    }

    @Test
    public void testExplicitPropertyMappings()
    {
        Map<String, String> properties = ImmutableMap.<String, String>builder()
                .put("fs.hadoop.enabled", "false")
                .put("fs.native-azure.enabled", "true")
                .put("fs.native-s3.enabled", "true")
                .put("fs.native-gcs.enabled", "true")
                .put("fs.s3-express.enabled", "false")
                .put("fs.cache", "alluxio")
                .buildOrThrow();

        FileSystemConfig expected = new FileSystemConfig()
                .setHadoopEnabled(false)
                .setNativeAzureEnabled(true)
                .setNativeS3Enabled(true)
                .setNativeGcsEnabled(true)
                .setNativeS3ExpressEnabled(false)
                .setCacheType(ALLUXIO);

        assertFullMapping(properties, expected);
    }
}
