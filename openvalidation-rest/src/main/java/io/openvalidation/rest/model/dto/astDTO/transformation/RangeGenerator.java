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

package io.openvalidation.rest.model.dto.astDTO.transformation;

import io.openvalidation.common.ast.ASTItem;
import io.openvalidation.common.ast.operand.ASTOperandStatic;
import io.openvalidation.rest.model.dto.astDTO.Position;
import io.openvalidation.rest.model.dto.astDTO.Range;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RangeGenerator {
  private List<String> outerLines;
  private Range outerRange;

  public RangeGenerator(String text) {
    this.outerLines = !text.isEmpty() ? Arrays.asList(text.split("\n")) : null;
    this.outerRange = null;
  }

  public RangeGenerator(DocumentSection section) {
    if (section != null) {
      this.outerLines = section.getLines();
      this.outerRange = section.getRange();
    } else {
      this.outerLines = null;
      this.outerRange = null;
    }
  }

  public DocumentSection generate(ASTItem innerElement) {
    String sourceText = this.getOriginalSource(innerElement);
    return this.generate(sourceText);
  }

  public DocumentSection generate(String sourceText) {
    if (sourceText == null) return null;

    List<String> innerLines = Arrays.asList(sourceText.split("\n"));
    innerLines = innerLines.stream().filter(l -> !l.isEmpty()).collect(Collectors.toList());

    if (innerLines.size() == 0 || this.outerLines == null || this.outerLines.size() == 0)
      return null;

    int outerStartLine = 0;
    int outerStartColumn = 0;
    if (this.outerRange != null &&
        this.outerRange.getStart() != null) {
      outerStartLine = this.outerRange.getStart().getLine();
      outerStartColumn = this.outerRange.getStart().getColumn();
    }

    String startLine = innerLines.get(0);
    String endLine = innerLines.get(innerLines.size() - 1);
    Position startPosition = null;
    Position endPosition = null;

    int lineNumber = 0;

    for (String line : this.outerLines) {
      int startLineIndex = line.indexOf(startLine);
      if (startLineIndex != -1) {
        int startLineNumber = outerStartLine + lineNumber;

        int startColumnNumber = startLineIndex;
        if (startLineNumber == outerStartLine) startColumnNumber += outerStartColumn;

        startPosition = new Position(startLineNumber, startColumnNumber);
      }

      int endLineIndex = line.indexOf(endLine);
      if (endLineIndex != -1) {
        int column =
            startLine.equals(endLine)
                ? startPosition.getColumn() + endLine.length()
                : endLineIndex + endLine.length();
        endPosition = new Position(outerStartLine + lineNumber, column);
      }

      if (endPosition != null && startPosition != null) break;

      lineNumber++;
    }

    Range range = new Range(startPosition, endPosition);
    return new DocumentSection(range, innerLines);
  }

  public String getOriginalSource(ASTItem element) {
    if (element == null) return "";

    if (element.getOriginalSource() != null) return element.getOriginalSource();

    if (element instanceof ASTOperandStatic) return ((ASTOperandStatic) element).getValue();

    return null;
  }
}
