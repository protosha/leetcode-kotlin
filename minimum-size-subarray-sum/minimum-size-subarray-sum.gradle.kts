plugins {
    application
}

application {
    mainClass.set("com.github.protosha.minimumsizesubarraysum.MinimumSizeSubarraySum")
}

dependencies {
    implementation(project(":utils"))
}