package codi.tool

class Repository {

    enum RepositoryStatus {
        notSync, failedAtInit, failedAtRefresh, inSync, sync
    }

    String name
    String url
    String path
    RepositoryStatus status = RepositoryStatus.notSync

    static hasMany = [branches: Branch]

    static constraints = {
        name blank: false, unique: true
        url blank: false, unique: true
        path blank: false, unique: true
        status nullable: false
    }

    def beginInit() {
        status = RepositoryStatus.inSync
        save()
    }

    def finishInit() {
        status = RepositoryStatus.sync
        save()
    }

    def failedAtInit() {
        status = RepositoryStatus.failedAtInit
        save()
    }
}
