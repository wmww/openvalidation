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

package io.openvalidation.common.ast.operand;

import io.openvalidation.common.data.DataPropertyType;
import java.util.Objects;

public class ASTOperandStaticString extends ASTOperandStatic {
  public ASTOperandStaticString(String value) {
    super((value != null) ? value.trim() : "");
    this.setDataType(DataPropertyType.String);
  }

  @Override
  public String toString() {
    return getValue();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ASTOperandStaticString)) return false;
    ASTOperandStaticString that = (ASTOperandStaticString) obj;
    return Objects.equals(getValue(), that.getValue());
  }
}