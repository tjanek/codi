package codi.tool

class Repository {

    enum RepositoryStatus {
        notSync, failed_at_init, failed_at_refresh, inSync, sync
    }

    String name
    String url
    String path
    RepositoryStatus status

    static hasMany = [branches: Branch]

    static constraints = {
        name blank: false, unique: true
        url blank: false, unique: true
        path blank: false, unique: true
        status nullable: false
    }
}
