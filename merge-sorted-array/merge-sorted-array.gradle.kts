plugins {
    application
}

application {
    mainClass.set("com.github.protosha.mergesortedarray.MergeSortedArray")
}

dependencies {
    implementation(project(":utils"))
}