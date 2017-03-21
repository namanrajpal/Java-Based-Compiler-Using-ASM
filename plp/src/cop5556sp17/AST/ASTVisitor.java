package cop5556sp17.AST;

public interface ASTVisitor {

	Object visitAssignmentStatement(AssignmentStatement assignStatement, Object arg) throws Exception;

	Object visitBinaryChain(BinaryChain binaryChain, Object arg) throws Exception;

	Object visitBinaryExpression(BinaryExpression binaryExpression, Object arg) throws Exception;

	Object visitBlock(Block block, Object arg) throws Exception;

	Object visitBooleanLitExpression(BooleanLitExpression booleanLitExpression, Object arg) throws Exception;

	Object visitConstantExpression(ConstantExpression constantExpression, Object arg);

	Object visitDec(Dec declaration, Object arg) throws Exception;

	Object visitFilterOpChain(FilterOpChain filterOpChain, Object arg) throws Exception;

	Object visitFrameOpChain(FrameOpChain frameOpChain, Object arg) throws Exception;

	Object visitIdentChain(IdentChain identChain, Object arg) throws Exception;

	Object visitIdentExpression(IdentExpression identExpression, Object arg) throws Exception;

	Object visitIdentLValue(IdentLValue identX, Object arg) throws Exception;

	Object visitIfStatement(IfStatement ifStatement, Object arg) throws Exception;

	Object visitImageOpChain(ImageOpChain imageOpChain, Object arg) throws Exception;

	Object visitIntLitExpression(IntLitExpression intLitExpression, Object arg) throws Exception;

	Object visitParamDec(ParamDec paramDec, Object arg) throws Exception;

	Object visitProgram(Program program, Object arg) throws Exception;

	Object visitSleepStatement(SleepStatement sleepStatement, Object arg) throws Exception;

	Object visitTuple(Tuple tuple, Object arg) throws Exception;

	Object visitWhileStatement(WhileStatement whileStatement, Object arg) throws Exception;

}
