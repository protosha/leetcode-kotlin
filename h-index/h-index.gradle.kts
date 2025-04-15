plugins {
    application
}

application {
    mainClass.set("com.github.protosha.hindex.HIndex")
}

dependencies {
    implementation(project(":utils"))
}