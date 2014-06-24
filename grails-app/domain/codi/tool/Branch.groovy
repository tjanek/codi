package codi.tool

class Branch {

    String name
    String prefix

    static belongsTo = [repository: Repository]
    static hasMany = [commits: Commit]

    static constraints = {
        name blank: false
        prefix blank: false
    }
}
