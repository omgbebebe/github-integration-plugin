package com.github.kostyasha.github.integration.generic.repoprovider;

import com.cloudbees.jenkins.GitHubRepositoryName;
import com.cloudbees.jenkins.GitHubWebHook;
import com.github.kostyasha.github.integration.generic.GitHubRepoProvider;
import hudson.Extension;
import hudson.model.Job;
import org.jenkinsci.plugins.github.GitHubPlugin;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.CheckForNull;
import java.util.Iterator;

/**
 * Standard github-plugin global configuration provider.
 * Defines connection based on globally configured github api servername + token.
 *
 * @author Kanstantsin Shautsou
 */
public class GitHubPluginRepoProvider extends GitHubRepoProvider {
    // possible cache connection/repo here

    @DataBoundConstructor
    public GitHubPluginRepoProvider() {
    }

    @Override
    public void registerHookFor(Job job) {
        GitHubWebHook.get().registerHookFor(job);
    }

    @Override
    public boolean isManageHooks(GitHubRepositoryName name, Job job) {
        // not exact @see https://github.com/jenkinsci/github-plugin/pull/149
        return GitHubPlugin.configuration().isManageHooks();
    }

    @Override
    public GitHub getGitHub(GitHubRepositoryName name, Job job) {
        return null;
    }

    @CheckForNull
    @Override
    public GHRepository getRepo(GitHubRepositoryName name, Job job) {
        // first matched from global config
        Iterator<GHRepository> resolved = name.resolve().iterator();
        if (resolved.hasNext()) {
            return resolved.next();
        }

        return null;
    }

    @Extension
    public static class DescriptorImpl extends GitHubRepoProviderDescriptor {
        @Override
        public String getDisplayName() {
            return "GitHub Plugin Repository Provider";
        }
    }
}
