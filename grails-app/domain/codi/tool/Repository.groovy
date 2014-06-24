package codi.tool

class Repository {

    enum RepositoryStatus {
        notSync, inSync, sync
    }

    String name
    String url
    String path
    RepositoryStatus status

    static constraints = {
        name blank: false, unique: true
        url blank: false, unique: true
        path blank: false, unique: true
        status nullable: false
    }
}
