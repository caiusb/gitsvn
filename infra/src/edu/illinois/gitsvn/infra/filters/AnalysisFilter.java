package edu.illinois.gitsvn.infra.filters;

import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.PipelineCommitFilter;

/**
 *	Represents an Analysis over the commits. (the commit analysis visitor).
 *	Will most likely end up as a template.
 * @author mihai
 */
public abstract class AnalysisFilter extends CommitFilter implements AnalysisLifecycle{

	protected PipelineCommitFilter filter;
	protected final String projectName;
	
	public AnalysisFilter(PipelineCommitFilter filter, String projectName) {
		this.filter = filter;
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
}
