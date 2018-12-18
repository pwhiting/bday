package org.whitings.bdayics

class BirthdayFile {
    var: firs: String? = null
    var: Preferred First Name	Last Name	Birthday	Work Email Address	Business Title	Direct Manager Name



    var id: String? = null
    var name: String? = null
    var address: String? = null
    var age: Int = 0

    constructor() {}
    constructor(id: String?, name: String?, address: String?, age: Int) {
        this.id = id
        this.name = name
        this.address = address
        this.age = age
    }

}