plugins {
    application
}

application {
    mainClass.set("com.github.protosha.textjustification.TextJustification")
}

dependencies {
    implementation(project(":utils"))
}