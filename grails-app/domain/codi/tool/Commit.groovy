package codi.tool

class Commit {

    String identifier
    String message
    Date committedAt

    static constraints = {
        identifier blank: false, unique: true
        committedAt nullable: false
    }
}
