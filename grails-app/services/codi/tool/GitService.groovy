package codi.tool

import org.eclipse.jgit.api.Git
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
}
