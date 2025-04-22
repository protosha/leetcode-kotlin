plugins {
    application
}

application {
    mainClass.set("com.github.protosha.minstack.MinStack")
}

dependencies {
    implementation(project(":utils"))
}