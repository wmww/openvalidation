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

package io.openvalidation.core.preprocessing.steps;

import io.openvalidation.common.utils.Constants;
import io.openvalidation.core.preprocessing.PreProcessorStepBase;

public class PreProcessorLastParagraphCleanup extends PreProcessorStepBase {

  @Override
  public String process(String rule) throws Exception {
    return rule.replaceAll(" " + Constants.PARAGRAPH_TOKEN + " ", " " + Constants.PARAGRAPH_TOKEN)
        .replaceAll(Constants.PARAGRAPH_TOKEN + " ", " " + Constants.PARAGRAPH_TOKEN);
  }
}
