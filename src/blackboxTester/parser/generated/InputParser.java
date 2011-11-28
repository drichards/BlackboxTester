/* Generated By:JavaCC: Do not edit this line. InputParser.java */
package blackboxTester.parser.generated;

import blackboxTester.parser.ast.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class InputParser implements InputParserConstants {
    protected Stack<ArrayList<Object>> currentObjectList = new Stack<ArrayList<Object>>();
    private HashSet<String> declaredTypes =  new HashSet<String>();
    private HashSet<String> seenTypes = new HashSet<String>();
    private HashSet<String> operations = new HashSet<String>();

    public <T> void openListContext(Class<T> className) {
        currentObjectList.push((ArrayList<Object>)new ArrayList<T>());
    }

    public <T> ArrayList<T> popCurrentObjectList(Class<T> className) {
        return (ArrayList<T>)currentObjectList.pop();
    }

  final public Input Input() throws ParseException {
    ArrayList<Signature> signatures;
    ArrayList<Equation> equations;
      openListContext(Signature.class);
    jj_consume_token(SIGNATURES);
    label_1:
    while (true) {
      Signature();
      if (jj_2_1(2)) {
        ;
      } else {
        break label_1;
      }
    }
        signatures = popCurrentObjectList(Signature.class);
        openListContext(Equation.class);
    jj_consume_token(EQUATIONS);
    label_2:
    while (true) {
      Equation();
      if (jj_2_2(2)) {
        ;
      } else {
        break label_2;
      }
    }
    jj_consume_token(0);
        for (String type : seenTypes) {
            if (!declaredTypes.contains(type)) {
                {if (true) throw new ParseException("Undeclared type: \u005c"" + type + "\u005c"");}
            }
        }

        equations = popCurrentObjectList(Equation.class);

        {if (true) return new Input(signatures, equations);}
    throw new Error("Missing return statement in function");
  }

  final public void Signature() throws ParseException {
    String typename;
      openListContext(OpSpec.class);
    jj_consume_token(ADT);
    typename = Typename();
    label_3:
    while (true) {
      OperationSpec();
      if (jj_2_3(2)) {
        ;
      } else {
        break label_3;
      }
    }
        declaredTypes.add(typename);
        ArrayList<OpSpec> specs = popCurrentObjectList(OpSpec.class);
        currentObjectList.peek().add(new Signature(typename, specs));
  }

  final public String Typename() throws ParseException {
    jj_consume_token(ID);
        {if (true) return token.image;}
    throw new Error("Missing return statement in function");
  }

  final public void OperationSpec() throws ParseException {
    String operation;
    Type returnType;
      openListContext(Type.class);
    operation = Operation();
    jj_consume_token(COLON);
    if (jj_2_5(2)) {
      Type(true);
      label_4:
      while (true) {
        if (jj_2_4(2)) {
          ;
        } else {
          break label_4;
        }
        jj_consume_token(ASTERIX);
        Type(true);
      }
    } else {
      ;
    }
    jj_consume_token(ARROW);
    returnType = Type(false);
        operations.add(operation);
        ArrayList<Type> argTypes = popCurrentObjectList(Type.class);
        currentObjectList.peek().add(new OpSpec(operation, argTypes, returnType));
  }

  final public String Operation() throws ParseException {
    jj_consume_token(ID);
      {if (true) return token.image;}
    throw new Error("Missing return statement in function");
  }

  final public Type Type(boolean addToListContext) throws ParseException {
    Type type = null;
    if (jj_2_6(2)) {
      jj_consume_token(INTEGER);
    } else if (jj_2_7(2)) {
      jj_consume_token(BOOLEAN);
    } else if (jj_2_8(2)) {
      jj_consume_token(CHARACTER);
    } else if (jj_2_9(2)) {
      jj_consume_token(STRING);
    } else if (jj_2_10(2)) {
      jj_consume_token(ID);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
        if (token.kind == InputParserConstants.ID) {
            seenTypes.add(token.image);
            type = new DynamicType(token.image);
        } else if (token.kind == InputParserConstants.INTEGER) {
            type = new PrimitiveType.IntegerType();
        } else if (token.kind == InputParserConstants.BOOLEAN) {
            type = new PrimitiveType.BooleanType();
        } else if (token.kind == InputParserConstants.CHARACTER) {
            type = new PrimitiveType.CharacterType();
        } else if (token.kind == InputParserConstants.STRING) {
            type = new PrimitiveType.StringType();
        }

        if (addToListContext) {
            currentObjectList.peek().add(type);
        }

        {if (true) return type;}
    throw new Error("Missing return statement in function");
  }

  final public void Equation() throws ParseException {
    Term leftHandSide;
    RHS rightHandSide;
    HashSet<String> seenVariables = new HashSet<String>();
    leftHandSide = Term(seenVariables, false);
    jj_consume_token(EQUALS);
    rightHandSide = RHS(seenVariables, false);
        currentObjectList.peek().add(new Equation(leftHandSide, rightHandSide));
  }

  final public Term Term(HashSet<String> seenVariables, boolean addToList) throws ParseException {
    Term term;
    if (jj_2_11(2)) {
      term = Variable(seenVariables, true);
    } else if (jj_2_12(2)) {
      term = RewriteOperation(seenVariables);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
        if (addToList) {
            currentObjectList.peek().add(term);
        }
        {if (true) return term;}
    throw new Error("Missing return statement in function");
  }

  final public RHS RHS(HashSet<String> seenVariables, boolean addToList) throws ParseException {
    RHS rhs;
    if (jj_2_13(2)) {
      rhs = RHSPrimitive();
    } else if (jj_2_14(2)) {
      rhs = Variable(seenVariables, false);
    } else if (jj_2_15(2)) {
      rhs = RHSPrimitiveOperation(seenVariables);
    } else if (jj_2_16(2)) {
      rhs = RHSOperation(seenVariables);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
        if (addToList) {
            currentObjectList.peek().add(rhs);
        }
        {if (true) return rhs;}
    throw new Error("Missing return statement in function");
  }

  final public RHS RHSPrimitive() throws ParseException {
    RHS rhs;
    if (jj_2_17(2)) {
      jj_consume_token(TRUE);
    } else if (jj_2_18(2)) {
      jj_consume_token(FALSE);
    } else if (jj_2_19(2)) {
      jj_consume_token(UINT10);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
        if (token.kind == InputParserConstants.TRUE) {
            rhs = new RHSTrue();
        } else if (token.kind == InputParserConstants.FALSE) {
            rhs = new RHSFalse();
        } else {
            rhs = new RHSInteger(Integer.valueOf(token.image));
        }

        {if (true) return rhs;}
    throw new Error("Missing return statement in function");
  }

  final public RHSPrimitiveOperation RHSPrimitiveOperation(HashSet<String> seenVariables) throws ParseException {
    String operation;
      openListContext(RHS.class);
    jj_consume_token(LEFT_PAREN);
    operation = RHSPrimitiveOperationName();
    label_5:
    while (true) {
      if (jj_2_20(2)) {
        ;
      } else {
        break label_5;
      }
      RHS(seenVariables, true);
    }
    jj_consume_token(RIGHT_PAREN);
        {if (true) return new RHSPrimitiveOperation(operation, popCurrentObjectList(RHS.class));}
    throw new Error("Missing return statement in function");
  }

  final public String RHSPrimitiveOperationName() throws ParseException {
    if (jj_2_21(2)) {
      jj_consume_token(NOT);
    } else if (jj_2_22(2)) {
      jj_consume_token(PLUS);
    } else if (jj_2_23(2)) {
      jj_consume_token(MINUS);
    } else if (jj_2_24(2)) {
      jj_consume_token(ASTERIX);
    } else if (jj_2_25(2)) {
      jj_consume_token(EQUALS);
    } else if (jj_2_26(2)) {
      jj_consume_token(LESS_THAN);
    } else if (jj_2_27(2)) {
      jj_consume_token(GREATER_THAN);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return token.image;}
    throw new Error("Missing return statement in function");
  }

  final public RHSOperation RHSOperation(HashSet<String> seenVariables) throws ParseException {
    String operation;
      openListContext(RHS.class);
    jj_consume_token(LEFT_PAREN);
    operation = Operation();
    label_6:
    while (true) {
      if (jj_2_28(2)) {
        ;
      } else {
        break label_6;
      }
      RHS(seenVariables, true);
    }
    jj_consume_token(RIGHT_PAREN);
        if(!operations.contains(operation)) {
            {if (true) throw new ParseException("Illegal operation: " + operation);}
        }

        {if (true) return new RHSOperation(operation, popCurrentObjectList(RHS.class));}
    throw new Error("Missing return statement in function");
  }

  final public Variable Variable(HashSet<String> seenVariables, boolean leftHandSide) throws ParseException {
    jj_consume_token(ID);
        if (leftHandSide && seenVariables.contains(token.image)) {
            {if (true) throw new ParseException("Duplicated variable: " + token.image);}
        } else if (leftHandSide) {
            seenVariables.add(token.image);
        } else if (!leftHandSide && !seenVariables.contains(token.image)) {
            {if (true) throw new ParseException("Unknown variable: " + token.image);}
        }

        {if (true) return new Variable(token.image);}
    throw new Error("Missing return statement in function");
  }

  final public Operation RewriteOperation(HashSet<String> seenVariables) throws ParseException {
    String operation;
      openListContext(Term.class);
    jj_consume_token(LEFT_PAREN);
    operation = Operation();
    label_7:
    while (true) {
      if (jj_2_29(2)) {
        ;
      } else {
        break label_7;
      }
      Term(seenVariables, true);
    }
    jj_consume_token(RIGHT_PAREN);
        if(!operations.contains(operation)) {
            {if (true) throw new ParseException("Illegal operation: " + operation);}
        }

        {if (true) return new Operation(operation, popCurrentObjectList(Term.class));}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_2_5(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  private boolean jj_2_6(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  private boolean jj_2_7(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_7(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(6, xla); }
  }

  private boolean jj_2_8(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_8(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(7, xla); }
  }

  private boolean jj_2_9(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_9(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(8, xla); }
  }

  private boolean jj_2_10(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_10(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(9, xla); }
  }

  private boolean jj_2_11(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_11(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(10, xla); }
  }

  private boolean jj_2_12(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_12(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(11, xla); }
  }

  private boolean jj_2_13(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_13(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(12, xla); }
  }

  private boolean jj_2_14(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_14(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(13, xla); }
  }

  private boolean jj_2_15(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_15(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(14, xla); }
  }

  private boolean jj_2_16(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_16(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(15, xla); }
  }

  private boolean jj_2_17(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_17(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(16, xla); }
  }

  private boolean jj_2_18(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_18(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(17, xla); }
  }

  private boolean jj_2_19(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_19(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(18, xla); }
  }

  private boolean jj_2_20(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_20(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(19, xla); }
  }

  private boolean jj_2_21(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_21(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(20, xla); }
  }

  private boolean jj_2_22(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_22(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(21, xla); }
  }

  private boolean jj_2_23(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_23(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(22, xla); }
  }

  private boolean jj_2_24(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_24(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(23, xla); }
  }

  private boolean jj_2_25(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_25(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(24, xla); }
  }

  private boolean jj_2_26(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_26(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(25, xla); }
  }

  private boolean jj_2_27(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_27(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(26, xla); }
  }

  private boolean jj_2_28(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_28(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(27, xla); }
  }

  private boolean jj_2_29(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_29(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(28, xla); }
  }

  private boolean jj_3_22() {
    if (jj_scan_token(PLUS)) return true;
    return false;
  }

  private boolean jj_3_21() {
    if (jj_scan_token(NOT)) return true;
    return false;
  }

  private boolean jj_3R_21() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_21()) {
    jj_scanpos = xsp;
    if (jj_3_22()) {
    jj_scanpos = xsp;
    if (jj_3_23()) {
    jj_scanpos = xsp;
    if (jj_3_24()) {
    jj_scanpos = xsp;
    if (jj_3_25()) {
    jj_scanpos = xsp;
    if (jj_3_26()) {
    jj_scanpos = xsp;
    if (jj_3_27()) return true;
    }
    }
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3_6() {
    if (jj_scan_token(INTEGER)) return true;
    return false;
  }

  private boolean jj_3R_11() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_6()) {
    jj_scanpos = xsp;
    if (jj_3_7()) {
    jj_scanpos = xsp;
    if (jj_3_8()) {
    jj_scanpos = xsp;
    if (jj_3_9()) {
    jj_scanpos = xsp;
    if (jj_3_10()) return true;
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(LEFT_PAREN)) return true;
    if (jj_3R_21()) return true;
    return false;
  }

  private boolean jj_3R_20() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  private boolean jj_3_19() {
    if (jj_scan_token(UINT10)) return true;
    return false;
  }

  private boolean jj_3_29() {
    if (jj_3R_18()) return true;
    return false;
  }

  private boolean jj_3_3() {
    if (jj_3R_10()) return true;
    return false;
  }

  private boolean jj_3_12() {
    if (jj_3R_13()) return true;
    return false;
  }

  private boolean jj_3_18() {
    if (jj_scan_token(FALSE)) return true;
    return false;
  }

  private boolean jj_3_14() {
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3R_10() {
    if (jj_3R_20()) return true;
    if (jj_scan_token(COLON)) return true;
    return false;
  }

  private boolean jj_3_17() {
    if (jj_scan_token(TRUE)) return true;
    return false;
  }

  private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_17()) {
    jj_scanpos = xsp;
    if (jj_3_18()) {
    jj_scanpos = xsp;
    if (jj_3_19()) return true;
    }
    }
    return false;
  }

  private boolean jj_3R_19() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  private boolean jj_3_13() {
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3_16() {
    if (jj_3R_16()) return true;
    return false;
  }

  private boolean jj_3R_17() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_13()) {
    jj_scanpos = xsp;
    if (jj_3_14()) {
    jj_scanpos = xsp;
    if (jj_3_15()) {
    jj_scanpos = xsp;
    if (jj_3_16()) return true;
    }
    }
    }
    return false;
  }

  private boolean jj_3_27() {
    if (jj_scan_token(GREATER_THAN)) return true;
    return false;
  }

  private boolean jj_3_28() {
    if (jj_3R_17()) return true;
    return false;
  }

  private boolean jj_3R_8() {
    if (jj_scan_token(ADT)) return true;
    if (jj_3R_19()) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(LEFT_PAREN)) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  private boolean jj_3_20() {
    if (jj_3R_17()) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_9()) return true;
    return false;
  }

  private boolean jj_3_26() {
    if (jj_scan_token(LESS_THAN)) return true;
    return false;
  }

  private boolean jj_3_11() {
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3R_18() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_11()) {
    jj_scanpos = xsp;
    if (jj_3_12()) return true;
    }
    return false;
  }

  private boolean jj_3_10() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_8()) return true;
    return false;
  }

  private boolean jj_3_25() {
    if (jj_scan_token(EQUALS)) return true;
    return false;
  }

  private boolean jj_3_9() {
    if (jj_scan_token(STRING)) return true;
    return false;
  }

  private boolean jj_3R_9() {
    if (jj_3R_18()) return true;
    if (jj_scan_token(EQUALS)) return true;
    return false;
  }

  private boolean jj_3R_12() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  private boolean jj_3_24() {
    if (jj_scan_token(ASTERIX)) return true;
    return false;
  }

  private boolean jj_3_4() {
    if (jj_scan_token(ASTERIX)) return true;
    if (jj_3R_11()) return true;
    return false;
  }

  private boolean jj_3_8() {
    if (jj_scan_token(CHARACTER)) return true;
    return false;
  }

  private boolean jj_3_23() {
    if (jj_scan_token(MINUS)) return true;
    return false;
  }

  private boolean jj_3_15() {
    if (jj_3R_15()) return true;
    return false;
  }

  private boolean jj_3R_16() {
    if (jj_scan_token(LEFT_PAREN)) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  private boolean jj_3_5() {
    if (jj_3R_11()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_4()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3_7() {
    if (jj_scan_token(BOOLEAN)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public InputParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[0];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[29];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public InputParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public InputParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new InputParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 0; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 0; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public InputParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new InputParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 0; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 0; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public InputParser(InputParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 0; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(InputParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 0; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[54];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 0; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 54; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 29; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
            case 5: jj_3_6(); break;
            case 6: jj_3_7(); break;
            case 7: jj_3_8(); break;
            case 8: jj_3_9(); break;
            case 9: jj_3_10(); break;
            case 10: jj_3_11(); break;
            case 11: jj_3_12(); break;
            case 12: jj_3_13(); break;
            case 13: jj_3_14(); break;
            case 14: jj_3_15(); break;
            case 15: jj_3_16(); break;
            case 16: jj_3_17(); break;
            case 17: jj_3_18(); break;
            case 18: jj_3_19(); break;
            case 19: jj_3_20(); break;
            case 20: jj_3_21(); break;
            case 21: jj_3_22(); break;
            case 22: jj_3_23(); break;
            case 23: jj_3_24(); break;
            case 24: jj_3_25(); break;
            case 25: jj_3_26(); break;
            case 26: jj_3_27(); break;
            case 27: jj_3_28(); break;
            case 28: jj_3_29(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
