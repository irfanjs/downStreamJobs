package org.jenkinsci.plugins.dwnstreamjobs;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.BooleanParameterDefinition;
import hudson.model.ParameterDefinition;

public class ExecuteDownstreamJobsParameterDefinition extends
			 BooleanParameterDefinition {

	private boolean executeDownstream;
	
	@DataBoundConstructor
	public ExecuteDownstreamJobsParameterDefinition(boolean executeDownstream) {
		super("executeDownstream", executeDownstream, "Checked means you DO want to execute downstream jobs.");
		
		this.executeDownstream = executeDownstream;
	}
	
	@Extension
	public static class DescriptorImpl extends ParameterDescriptor {
		@Override
		public String getDisplayName() {
			return "Execute downstream jobs";
		}
	}
}