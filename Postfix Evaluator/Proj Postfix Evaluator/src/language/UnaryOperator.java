package language;

/**
 * A {@link UnaryOperator} is an {@link Operator} that performs an operation on one arguments.
 *
 * @param <T> they type of the {@link Operand} being evaluated
 */
public abstract class UnaryOperator<T> implements Operator<T> {

  protected Operand<T> op;

  /** {@inheritDoc} */
  @Override
  public final int getNumberOfArguments() {
    return 1;
  }

  /** {@inheritDoc} */
  @Override
  public void setOperand(int i, Operand<T> operand) {
    if (operand == null) throw new NullPointerException("Could not set null operand.");
    if (i > 0)
      throw new IllegalArgumentException(
          "Unary operator only accepts operands 0 but recieved " + i + ".");
    else if (i == 0) {
      if (op != null)
        throw new IllegalStateException("Position " + i + " has been previously set.");
      op = operand;
    } //else {
     // if (op1 != null)
     //   throw new IllegalStateException("Position " + i + " has been previously set.");
     // op1 = operand;
    //}
  }
}