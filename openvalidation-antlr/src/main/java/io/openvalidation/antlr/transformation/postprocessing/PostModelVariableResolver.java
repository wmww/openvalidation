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

package io.openvalidation.antlr.transformation.postprocessing;

import io.openvalidation.common.ast.ASTItem;
import io.openvalidation.common.ast.ASTModel;
import io.openvalidation.common.ast.ASTVariable;
import io.openvalidation.common.ast.operand.ASTOperandVariable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PostModelVariableResolver
    extends PostProcessorSubelementBase<ASTModel, ASTOperandVariable> {
  List<ASTVariable> variableDefinitions = null;

  @Override
  public void process(ASTItem item, PostProcessorContext context) {
    this.variableDefinitions = item.collectItemsOfType(ASTVariable.class);
    super.process(item, context);
  }

  @Override
  protected Predicate<? super ASTOperandVariable> getFilter() {
    return v -> v.isVariableUnresolved();
  }

  @Override
  protected void processItem(ASTOperandVariable varRef) {
    Optional<ASTVariable> def =
        variableDefinitions.stream()
            .filter(v -> v.getName().equals(varRef.getVariableName()))
            .findFirst();

    if (def.isPresent()) varRef.setVariable(def.get());
  }
}
