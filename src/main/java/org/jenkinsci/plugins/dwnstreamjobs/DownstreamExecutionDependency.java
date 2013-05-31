/**
 * 
 */
package org.jenkinsci.plugins.dwnstreamjobs;

import java.io.IOException;
import java.util.List;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.model.Action;
import hudson.model.JobProperty;
import hudson.model.TaskListener;
import hudson.plugins.downstream_ext.DownstreamDependency;
import hudson.plugins.downstream_ext.DownstreamTrigger;

public class DownstreamExecutionDependency extends DownstreamDependency {

	public DownstreamExecutionDependency(AbstractProject<?, ?> upstream,
			AbstractProject<?, ?> downstream, DownstreamTrigger trigger) {
		super(upstream, downstream, trigger);
	}
	
	
	@Override
	public boolean shouldTriggerBuild(AbstractBuild build,
			TaskListener listener, List<Action> actions) {
		try {
			if (build.getEnvironment(listener).containsKey("executeDownstream")) {
				if ("false".equals(build.getEnvironment(listener).get("executeDownstream"))) {
					listener.getLogger().println("Not executing downstream jobs due to variable executeDownstream is set to false.");
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			listener.getLogger().print(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			listener.getLogger().print(e.getMessage());
		}
		listener.getLogger().println("Executing downstream jobs due to variable executeDownstream is set to true.");

		return super.shouldTriggerBuild(build, listener, actions);
	}

}
