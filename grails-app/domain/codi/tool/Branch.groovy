package codi.tool

class Branch {

    String name
    String prefix

    static constraints = {
        name blank: false, unique: true
        prefix blank: false
    }
}
