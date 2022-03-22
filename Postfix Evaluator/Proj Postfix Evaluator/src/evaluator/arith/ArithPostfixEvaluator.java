package evaluator.arith;

import language.Operand;
import language.Operator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/** An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions. */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /** Constructs an {@link ArithPostfixEvaluator} */
  public ArithPostfixEvaluator() {
    // TODO Initialize to your LinkedStack
    stack = new LinkedStack<Operand<Integer>>();
    
  }

  /** {@inheritDoc} */
  @Override
  public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
    ArithPostfixParser parser = new ArithPostfixParser(expr);
    int temp = -1;
    for (Token<Integer> token : parser) {
      Type type = token.getType();
      switch (type) {
        case OPERAND:
          // TODO What do we do when we see an operand?
          stack.push(token.getOperand());
          if(stack.size()>1){
            temp = 0;
          }
          break;
        case OPERATOR:
          // TODO What do we do when we see an operator?
          Operator<Integer> operator = token.getOperator();
          temp = 1;
          if(operator.getNumberOfArguments() == 2){
            if(stack.size() < 2){
              throw new IllegalPostfixExpressionException();
            }
            operator.setOperand(1, stack.pop());
            operator.setOperand(0, stack.pop());
            stack.push(operator.performOperation());
          } else if(operator.getNumberOfArguments() == 1){
            if(stack.size() < 1){
              throw new IllegalPostfixExpressionException();
            }
            operator.setOperand(0, stack.pop());
            stack.push(operator.performOperation());
          }
          break;
        default:
          throw new IllegalStateException("Parser returned an invalid Type: " + type);
      }
    }
    if(temp == 0){
      throw new IllegalPostfixExpressionException();
    }
    // TODO What do we return?
    return stack.pop().getValue();
  }
}
