plugins {
    application
}

application {
    mainClass.set("com.github.protosha.lettercasepermutation.LetterCasePermutation")
}

dependencies {
    implementation(project(":utils"))
}