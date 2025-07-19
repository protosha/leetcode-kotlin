plugins {
    application
}

application {
    mainClass.set("com.github.protosha.missingnumber.MissingNumber")
}

dependencies {
    implementation(project(":utils"))
}