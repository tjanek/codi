package codi.tool

class RepositoryImportJob {

    def concurrent = false
    def repositoryImportService

    static triggers = {
      simple startDelay: 60 * 1000L,
             repeatInterval: 30 * 1000L
    }

    def execute() {
        log.debug "Starting repository import job"
        repositoryImportService.initialImportRepositories()
    }
}
