/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.6/userguide/multi_project_builds.html
 */

include("median-of-two-sorted-arrays")
include("sudoku-solver")
include("skeleton")
include("utils")

rootProject.name = "leetcode-kotlin"
rootProject.children.forEach { it.buildFileName = "${it.name}.gradle.kts" }
