plugins {
    application
}

application {
    mainClass.set("com.github.protosha.sudokusolver.SudokuSolver")
}

dependencies {
    implementation(project(":utils"))
}