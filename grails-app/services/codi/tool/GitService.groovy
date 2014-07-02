package codi.tool

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.ObjectReader
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.CanonicalTreeParser

class GitService {

    static transactional = false

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
        .call()
    }

    def pull(String root) {
        git(root).pull().call()
    }

    def commits(String root) {
        git(root).log().all().call()
    }

    def commitsInBranch(String root, String branch) {
        def commitsInBranch = []
        def repo = repo(root)
        def revWalk = new RevWalk(repo)
        try {
            git(repo).log().call().each { RevCommit commit ->
                def targetCommit = revWalk.parseCommit(repo.resolve(commit.getName()))
                repo.allRefs.entrySet().each { ref ->
                    def branchNameMatches = (Constants.R_HEADS + branch).equals(ref.value.name)
                    def commitIsInBranch = revWalk.isMergedInto(targetCommit, revWalk.parseCommit(ref.value.objectId))
                    if (branchNameMatches && commitIsInBranch) {
                        commitsInBranch += commit
                    }
                }
            }
        } finally {
            revWalk.release()
        }
        commitsInBranch
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

    def diff(String root, String commitId) {
        def localRepo = repo(root)

        def oldTreeIterator = treeIterator(localRepo, commitId)
        def newTreeIterator = treeIterator(localRepo, "$commitId~1")

        List<DiffEntry> diffs = new Git(localRepo)
            .diff()
            .setOldTree(oldTreeIterator)
            .setNewTree(newTreeIterator)
        .call()

        ByteArrayOutputStream out = new ByteArrayOutputStream()
        DiffFormatter df = new DiffFormatter(out);
        df.repository = localRepo

        def output = []
        for (DiffEntry diff : diffs) {
            df.format(diff)
            output += out.toString("UTF-8").split('\n')
            out.reset()
        }
        df.release()
        localRepo.close()
        output
    }

    def treeIterator(Repository repo, String commitId) {
        ObjectId commit = repo.resolve("$commitId^{tree}")
        if (commit != null) {
            CanonicalTreeParser treeIterator = new CanonicalTreeParser()
            ObjectReader reader = repo.newObjectReader()
            treeIterator.reset(reader, commit)
            return treeIterator
        }
        null
    }

}
