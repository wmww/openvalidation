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

package io.openvalidation.antlr.test.util.parsers;

import io.openvalidation.antlr.generated.mainLexer;
import io.openvalidation.antlr.generated.mainParser;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

public class IncompleteInputParser {
  public static List<String> parse(String plainText) {
    CharStream inputStream = CharStreams.fromString(plainText);
    mainLexer lexer = new mainLexer(inputStream);
    TokenStream tokens = new CommonTokenStream(lexer);
    mainParser parser = new mainParser(tokens);

    parser.removeErrorListeners();
    IncompleteInputErrorListener errorListener = new IncompleteInputErrorListener();
    parser.addErrorListener(errorListener);

    parser.main();

    return errorListener.getErrorList();
  }
}
