plugins {
    application
}

application {
    mainClass.set("com.github.protosha.candy.Candy")
}

dependencies {
    implementation(project(":utils"))
}