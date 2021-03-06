/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.runners.direct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.beam.runners.direct.DirectRegistrar.DirectRunner;
import org.apache.beam.sdk.options.PipelineOptionsRegistrar;
import org.apache.beam.sdk.runners.PipelineRunnerRegistrar;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ServiceLoader;

/** Tests for {@link DirectRunner}. */
@RunWith(JUnit4.class)
public class DirectRegistrarTest {
  @Test
  public void testCorrectOptionsAreReturned() {
    assertEquals(
        ImmutableList.of(DirectOptions.class),
        new DirectRegistrar.DirectOptions().getPipelineOptions());
  }

  @Test
  public void testCorrectRunnersAreReturned() {
    assertEquals(
        ImmutableList.of(org.apache.beam.runners.direct.DirectRunner.class),
        new DirectRunner().getPipelineRunners());
  }

  @Test
  public void testServiceLoaderForOptions() {
    for (PipelineOptionsRegistrar registrar :
        Lists.newArrayList(ServiceLoader.load(PipelineOptionsRegistrar.class).iterator())) {
      if (registrar instanceof DirectRegistrar.DirectOptions) {
        return;
      }
    }
    fail("Expected to find " + DirectRegistrar.DirectOptions.class);
  }

  @Test
  public void testServiceLoaderForRunner() {
    for (PipelineRunnerRegistrar registrar :
        Lists.newArrayList(ServiceLoader.load(PipelineRunnerRegistrar.class).iterator())) {
      if (registrar instanceof DirectRunner) {
        return;
      }
    }
    fail("Expected to find " + DirectRunner.class);
  }
}
