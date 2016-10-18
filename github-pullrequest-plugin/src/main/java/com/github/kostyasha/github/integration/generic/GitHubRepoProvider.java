package com.github.kostyasha.github.integration.generic;

import com.cloudbees.jenkins.GitHubRepositoryName;
import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.Job;
import jenkins.model.Jenkins;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import javax.annotation.Nullable;

/**
 * Extension for providing GH connection for specified repository with job context.
 * You can extract additional information from job to define what connection provide.
 *
 * @author Kanstantsin Shautsou
 */
public abstract class GitHubRepoProvider extends AbstractDescribableImpl<GitHubRepoProvider>
        implements ExtensionPoint {

    public abstract void registerHookFor(Job job);

    /**
     * Whether it allowed to manage hooks for certain job.
     */
    public abstract boolean isManageHooks(GitHubRepositoryName name, Job job);

    /**
     * Not used yet because trigger needs only GHRepository to work.
     */
    @Nullable
    public abstract GitHub getGitHub(GitHubRepositoryName name, Job job);

    /**
     * alive connection to remote repo.
     */
    @Nullable
    public abstract GHRepository getRepo(GitHubRepositoryName name, Job job);

    public abstract static class GitHubRepoProviderDescriptor
            extends Descriptor<GitHubRepoProvider> {

        public static DescriptorExtensionList allRepoProviders() {
            return Jenkins.getActiveInstance().getDescriptorList(GitHubRepoProvider.class);
        }
    }
}
