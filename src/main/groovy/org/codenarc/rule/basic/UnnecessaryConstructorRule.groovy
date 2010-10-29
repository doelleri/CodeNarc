/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.basic

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import java.lang.reflect.Modifier

/**
 * UnnecessaryConstructor
 *
 * @author 'Tomasz Bujok'
 * @version $Revision: 24 $ - $Date: 2009-01-31 13:47:09 +0100 (Sat, 31 Jan 2009) $
 */
class UnnecessaryConstructorRule extends AbstractAstVisitorRule {
    String name = 'UnnecessaryConstructor'
    int priority = 2
    Class astVisitorClass = UnnecessaryConstructorAstVisitor
}

class UnnecessaryConstructorAstVisitor extends AbstractAstVisitor {

  def void visitClass(ClassNode node) {
    super.visitClass(node);
    if (node.constructors?.size() == 1) {
        analyzeConstructor node.constructors[0]
    }
  }

  private void analyzeConstructor(ConstructorNode node) {
     if(node.code?.isEmpty() && !Modifier.isPrivate(node.modifiers) && node.parameters?.size() == 0) {
        addViolation node
     }
  }
  
}
