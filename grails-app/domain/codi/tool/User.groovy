package codi.tool

class User {

    String email
    String password
    String name
    String avatar

    static constraints = {
        email blank: false, email: true, unique: true
        password blank: false, minSize: 8
        name nullable: false
        avatar nullable: true
    }

    def beforeInsert() {
        //TODO: encode password
    }

    def beforeUpdate() {
        //TODO: encode password
    }
}
