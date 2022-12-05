plugins {
    application
}

application {
    mainClass.set("com.github.protosha.skeleton.Skeleton")
}

dependencies {
    implementation(project(":utils"))
}