plugins {
    application
}

application {
    mainClass.set("com.github.protosha.lrucache.LruCache")
}

dependencies {
    implementation(project(":utils"))
}