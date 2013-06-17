package edu.illinois.gitsvn.infra.collectors;

public class CutoffDetectorFilter extends CutofDetectorCollector {

	public CutoffDetectorFilter(int cutofTime) {
		super(cutofTime);
	}
	
	@Override
	protected boolean treatGit() {
		return true;
	}
	
	@Override
	protected boolean treatSVN() {
		return false;
	}

}
