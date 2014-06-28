package codi.tool

class Commit {

    String identifier
    String message
    Date committedAt
    boolean approved

    static belongsTo = [branch: Branch]

    static constraints = {
        identifier blank: false, unique: true
        committedAt nullable: false
    }
}
