package org.jenkinsci.plugins.dwnstreamjobs;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.DependencyGraph;
import hudson.model.ItemGroup;
import hudson.model.Result;
import hudson.model.Descriptor.FormException;
import hudson.plugins.downstream_ext.DownstreamDependency;
import hudson.plugins.downstream_ext.MatrixTrigger;
import hudson.plugins.downstream_ext.DownstreamTrigger;
import org.jenkinsci.plugins.dwnstreamjobs.DownstreamExecutionDependency;
import hudson.tasks.Publisher;
import hudson.tasks.BuildTrigger;

public class DownStreamTrigger extends DownstreamTrigger {
	public DownStreamTrigger(String childProjects, String threshold, String strategy, boolean onlyIfParameterEnabled) {
		super(childProjects, threshold, false, false, strategy, null);
		this.onlyIfParameterEnabled = onlyIfParameterEnabled;
	}
	
	@Override
    public void buildDependencyGraph(AbstractProject owner, DependencyGraph graph) {
        ItemGroup context = owner.getParent();
    	for (AbstractProject downstream : getChildProjects(context)) {
    		graph.addDependency(new DownstreamExecutionDependency(owner, downstream, this));
    	}
	}

	public boolean onlyIfParameterEnabled;	
	
	public void setOnlyIfParameterEnabled(boolean value) { this.onlyIfParameterEnabled = value; }
	public boolean getOnlyIfParameterEnabled() { return this.onlyIfParameterEnabled; }
	
	@Extension
	public static class DescriptorImpl extends DownstreamTrigger.DescriptorImpl {
		@Override
		public String getDisplayName() {
			return "Execute downstream jobs.";
		}
		
        @Override
        public Publisher newInstance(StaplerRequest req, JSONObject formData) throws FormException {
			return new DownStreamTrigger(formData.getString("childProjects"), formData.getString("threshold"), 
					formData.getString("strategy"), formData.getBoolean("onlyIfParameterEnabled"));
        }
	}
}
