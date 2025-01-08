plugins {
    application
}

application {
    mainClass.set("com.github.protosha.removeduplicatesfromsortedarray.RemoveDuplicatesFromSortedArray")
}

dependencies {
    implementation(project(":utils"))
}