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

package io.openvalidation.rest.controller;

import io.openvalidation.common.ast.*;
import io.openvalidation.common.model.OpenValidationResult;
import io.openvalidation.common.utils.LINQ;
import io.openvalidation.rest.model.dto.UnkownElementParser;
import io.openvalidation.rest.model.dto.astDTO.MainNode;
import io.openvalidation.rest.model.dto.astDTO.ScopesErrorDTO;
import io.openvalidation.rest.model.dto.astDTO.transformation.TreeTransformer;
import io.openvalidation.rest.service.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/linting")
public class ContextParsingController {

  @Autowired private OpenValidationService ovService;

  @PostMapping
  public ResponseEntity<ScopesErrorDTO> generate(@RequestBody OVParams parameters) {
    if (parameters == null) {
      throw new ResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          "You did not provide any parameters for the generation request. You can browse the API on /swagger-ui.html");
    }

    OpenValidationResult result;
    List<ASTItem> astItemList;

    try {
      result = ovService.generate(parameters, false);

      ASTModel astModel = result.getASTModel();
      if (astModel == null) {
          astModel = new ASTModel();
          astModel.add(new ASTUnknown(parameters.getRule()));
      }
      astItemList = new UnkownElementParser(astModel, parameters).generate(ovService);

    } catch (Exception e) {
      e.printStackTrace();
      throw new OpenValidationResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Sorry something went wrong. We are working hard to fix the Problem.",
          e);
    }

    TreeTransformer transformer = new TreeTransformer(result, astItemList, parameters);
    MainNode node = transformer.transform(parameters.getRule());

    ScopesErrorDTO dto = new ScopesErrorDTO(node.getScopes(), result.getErrors());
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }
}