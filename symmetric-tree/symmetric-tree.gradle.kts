plugins {
    application
}

application {
    mainClass.set("com.github.protosha.symmetrictree.SymmetricTree")
}

dependencies {
    implementation(project(":utils"))
}