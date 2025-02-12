plugins {
    application
}

application {
    mainClass.set("com.github.protosha.majorityelement.MajorityElement")
}

dependencies {
    implementation(project(":utils"))
}