package codi.tool

import grails.transaction.Transactional

import static codi.tool.Repository.RepositoryStatus.failedAtInit
import static codi.tool.Repository.RepositoryStatus.notSync

@Transactional
class RepositoryImportService {

    def gitService

    def initialImportRepositories() {
        log.debug "Fetching all not imported repositories"
        Repository.findAllByStatusInList([notSync, failedAtInit]).each { repository ->
            initialImportRepository(repository)
        }
    }

    def initialImportRepository(Repository repository) {
        log.debug "Initial fetching repository '${repository.name}' from '${repository.url}' into '${repository.path}'"
        try {
            repository.beginInit()
            gitService.clone(repository.url, repository.path)
            repository.finishInit()
            log.debug "Successfully initial fetched repository '${repository.name}'"
        } catch (Exception e) {
            log.error "Failed to initial fetch repository '${repository.name}:", e
            repository.failedAtInit()
        }
    }
}
