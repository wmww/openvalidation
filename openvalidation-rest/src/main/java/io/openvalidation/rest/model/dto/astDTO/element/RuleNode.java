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

package io.openvalidation.rest.model.dto.astDTO.element;

import io.openvalidation.common.ast.ASTActionError;
import io.openvalidation.common.ast.ASTRule;
import io.openvalidation.common.utils.Constants;
import io.openvalidation.core.Aliases;
import io.openvalidation.rest.model.dto.astDTO.GenericNode;
import io.openvalidation.rest.model.dto.astDTO.TransformationHelper;
import io.openvalidation.rest.model.dto.astDTO.operation.ConditionNode;
import io.openvalidation.rest.model.dto.astDTO.operation.NodeMapper;
import io.openvalidation.rest.model.dto.astDTO.transformation.DocumentSection;
import io.openvalidation.rest.model.dto.astDTO.transformation.RangeGenerator;

import java.util.List;

public class RuleNode extends GenericNode {
  private ActionErrorNode errorNode;
  private ConditionNode condition;

  public RuleNode(ASTRule rule, DocumentSection section, String culture) {
    super.initializeElement(section);

    ASTActionError actionError = (ASTActionError) rule.getAction();
    if (actionError != null) {
      this.errorNode = this.generateErrorNode(actionError, culture);
    }

    if (rule.getCondition() != null) {
      DocumentSection newSection = new RangeGenerator(section).generate(rule.getCondition());
      this.condition =
          NodeMapper.createConditionNode(
              rule.getCondition(), newSection, rule.getOriginalSource(), culture);
    }
  }

  public ActionErrorNode generateErrorNode(ASTActionError actionError, String culture) {
    List<String> thenKeywordList = Aliases.getSpecificAliasByToken(culture, Constants.THEN_TOKEN);
    if (thenKeywordList.size() == 0) return new ActionErrorNode(null, actionError);

    String thenKeyword = thenKeywordList.get(0);
    String ruleLine = String.join("\n", this.getLines());
    String[] splittedRule = ruleLine.split("(?=(?i)" + thenKeyword + ")");
    if (splittedRule.length <= 1) return new ActionErrorNode(null, actionError);

    DocumentSection section = new RangeGenerator(this.getLines(), this.getRange()).generate(splittedRule[1]);
    return new ActionErrorNode(section, actionError);
  }

  public ActionErrorNode getErrorNode() {
    return errorNode;
  }

  public void setErrorNode(ActionErrorNode errorMessage) {
    this.errorNode = errorMessage;
  }

  public ConditionNode getCondition() {
    return condition;
  }

  public void setCondition(ConditionNode condition) {
    this.condition = condition;
  }
}
