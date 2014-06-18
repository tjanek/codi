package codi.tool

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

class GitService {

    def git(String root) {
        new Git(repo(root))
    }

    def git(Repository repo) {
        new Git(repo)
    }

    def repo(String root) {
        new FileRepositoryBuilder()
                .setWorkTree(new File(root))
                .setMustExist(true)
        .build()
    }

    def clone(String url, String path) {
        Git.cloneRepository()
            .setURI(url)
            .setDirectory(new File(path))
        .call();
    }

    def pull(String root) {
        git(root).pull().call()
    }

    def commits(String root) {
        git(root).log()
            .all()
        .call()
    }

    def refs(String root, String...prefixes) {
        def refs = []
        def repository = repo(root)
        prefixes.each { prefix -> refs += repository.refDatabase.getRefs(prefix).values() }
        refs
    }

    def localBranches(String root) {
        refs(root, Constants.R_HEADS)
    }

    def remoteBranches(String root) {
        refs(root, Constants.R_REMOTES)
    }

    def allBranches(String root) {
        localBranches(root) + remoteBranches(root)
    }

    def localBranchesNames(String root) {
        localBranches(root).collect { Ref branch -> refName(branch, Constants.R_HEADS) }
    }

    def remoteBranchesNames(String root) {
        remoteBranches(root).collect { Ref branch -> refName(branch, Constants.R_REMOTES) }
    }

    def allBranchesNames(String root) {
        localBranchesNames(root) + remoteBranchesNames(root)
    }

    def refName(Ref ref, String prefix = '') {
        ref.name.substring(prefix.length())
    }
}
