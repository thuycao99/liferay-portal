/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspace implements Workspace {

	@Override
	public void addJenkinsLocalGitBranch(String jenkinsGitHubURL) {
		if (jenkinsGitHubURL == null) {
			return;
		}

		if (!JenkinsResultsParserUtil.isCINode()) {
			return;
		}

		LocalGitRepository jenkinsLocalGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				"liferay-jenkins-ee", "master");

		LocalGitBranch localGitBranch;

		if (PullRequest.isValidGitHubPullRequestURL(jenkinsGitHubURL)) {
			PullRequest pullRequest = new PullRequest(jenkinsGitHubURL);

			localGitBranch = GitHubDevSyncUtil.createCachedLocalGitBranch(
				jenkinsLocalGitRepository, pullRequest, true);

			_jenkinsBranchName = GitHubDevSyncUtil.getCacheBranchName(
				pullRequest);
		}
		else if (GitUtil.isValidGitHubRefURL(jenkinsGitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(
				jenkinsGitHubURL);

			localGitBranch = GitHubDevSyncUtil.createCachedLocalGitBranch(
				jenkinsLocalGitRepository, remoteGitRef, true);

			_jenkinsBranchName = GitHubDevSyncUtil.getCacheBranchName(
				remoteGitRef);
		}
		else {
			throw new RuntimeException(
				"Invalid jenkins GitHub url " + jenkinsGitHubURL);
		}

		_jenkinsLocalGitBranch = localGitBranch;
	}

	@Override
	public String getJenkinsBranchName() {
		return _jenkinsBranchName;
	}

	@Override
	public void setupWorkspace() {
		checkoutLocalGitBranches();

		writeGitRepositoryPropertiesFiles();
	}

	protected BaseWorkspace() {
		boolean synchronizeGitBranches;

		if (this instanceof BatchWorkspace) {
			synchronizeGitBranches = false;
		}
		else if (this instanceof TopLevelWorkspace) {
			synchronizeGitBranches = true;
		}
		else {
			throw new RuntimeException("Invalid Workspace type");
		}

		_synchronizeGitBranches = synchronizeGitBranches;
	}

	protected void checkoutJenkinsLocalGitBranch() {
		if (_jenkinsLocalGitBranch != null) {
			checkoutLocalGitBranch(_jenkinsLocalGitBranch);
		}
	}

	protected void checkoutBranch(LocalGitBranch localGitBranch) {
		System.out.println();
		System.out.println("##");
		System.out.println("## " + localGitBranch.toString());
		System.out.println("##");
		System.out.println();

		GitWorkingDirectory gitWorkingDirectory =
			localGitBranch.getGitWorkingDirectory();

		gitWorkingDirectory.createLocalGitBranch(localGitBranch, true);

		gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch);

		gitWorkingDirectory.reset("--hard " + localGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();
	}

	protected boolean synchronizeGitBranches() {
		return _synchronizeGitBranches;
	}

	private String _jenkinsBranchName;
	private LocalGitBranch _jenkinsLocalGitBranch;
	private final boolean _synchronizeGitBranches;

}