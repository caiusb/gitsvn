package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.filter.tree.BaseTreeFilter;

//TODO make this into a configurable extension blocker and use factory methods for java blocker, source code blocker etc
public final class NonSourceCodeFileExtensionBlacklister extends BaseTreeFilter {
	
//	private static final List<String> allowedExtensions = Arrays.asList("java", "js", "c", "cpp", "h", "ino", "py");
	private static final List<String> allowedExtensions = Arrays.asList("java");
	
	@Override
	public boolean include(TreeWalk walker) throws MissingObjectException, IncorrectObjectTypeException, IOException {
		if (walker.isSubtree()){
			return true;
		}
		
		String name = walker.getNameString();
		final int extensionStart = name.lastIndexOf('.') + 1;
		
		// Ignore names that don't contain a '.' or end with a '.'
		if (extensionStart == 0 || extensionStart == name.length())
			return false;
		
		name = name.substring(extensionStart);
		
		return allowedExtensions.contains(name);
	}
}