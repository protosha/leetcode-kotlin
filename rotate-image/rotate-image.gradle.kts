plugins {
    application
}

application {
    mainClass.set("com.github.protosha.rotateimage.RotateImage")
}

dependencies {
    implementation(project(":utils"))
}