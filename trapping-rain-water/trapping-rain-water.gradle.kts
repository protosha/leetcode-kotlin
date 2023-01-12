plugins {
    application
}

application {
    mainClass.set("com.github.protosha.trappingrainwater.TrappingRainWater")
}

dependencies {
    implementation(project(":utils"))
}