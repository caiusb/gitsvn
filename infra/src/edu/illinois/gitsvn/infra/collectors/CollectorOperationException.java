package edu.illinois.gitsvn.infra.collectors;

import edu.illinois.codingtracker.tests.postprocessors.ast.ASTNodeOperationException;

/**
 * This class is a hack so I can round the gitective lack of
 * exceptions (propper ones).
 * @author caius
 *
 */
public class CollectorOperationException extends RuntimeException {

	public CollectorOperationException(ASTNodeOperationException e) {
		super(e);
	}
	
	public CollectorOperationException() {
		super();
	}

}
