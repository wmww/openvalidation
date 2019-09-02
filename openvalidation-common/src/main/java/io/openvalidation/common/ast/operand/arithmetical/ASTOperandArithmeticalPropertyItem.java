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

package io.openvalidation.common.ast.operand.arithmetical;

import io.openvalidation.common.ast.operand.property.ASTOperandProperty;
import java.util.ArrayList;
import java.util.List;

public class ASTOperandArithmeticalPropertyItem extends ASTOperandArithmeticalItemBase {

  public ASTOperandArithmeticalPropertyItem(ASTOperandProperty oprnd) {
    this.operand = oprnd;
    this._preprocessedSource = oprnd.getPreprocessedSource();
  }

  public ASTOperandProperty getOperand() {
    return (ASTOperandProperty) this.operand;
  }

  @Override
  public List<ASTOperandProperty> getProperties() {
    List<ASTOperandProperty> props = new ArrayList<>();

    if (this.operand != null) {
      props.add(this.getOperand());
    }

    return props;
  }

  @Override
  public String print(int level) {
    StringBuilder sb = new StringBuilder();

    if (this.getOperator() != null)
      sb.append(super.space(level) + this.getOperator().name() + "\n");

    sb.append(this.space(level) + this.getType() + " : \n" + this.getOperand().print(level + 1));

    return sb.toString();
  }
}
