/*
 *    Copyright 2019 BROCKHAUS AG
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.openvalidation.core.preprocessing;

import io.openvalidation.common.model.PreProcessorContext;

public abstract class PreProcessorStepBase {
  private PreProcessorContext _context;

  public PreProcessorStepBase() {}

  public PreProcessorStepBase(PreProcessorContext context) {
    this._context = context;
  }

  public PreProcessorContext getContext() {
    return _context;
  }

  public void setContext(PreProcessorContext context) {
    this._context = context;
  }

  public String process(String value) throws Exception {
    return value;
  }
}
