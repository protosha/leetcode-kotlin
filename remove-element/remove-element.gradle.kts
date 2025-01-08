plugins {
    application
}

application {
    mainClass.set("com.github.protosha.removeelement.RemoveElement")
}

dependencies {
    implementation(project(":utils"))
}