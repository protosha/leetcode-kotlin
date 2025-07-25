plugins {
    application
}

application {
    mainClass.set("com.github.protosha.invertbinarytree.InvertBinaryTree")
}

dependencies {
    implementation(project(":utils"))
}