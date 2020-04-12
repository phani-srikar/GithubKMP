enableFeaturePreview("GRADLE_METADATA")
rootProject.name="GithubKMP"
include(":shared")
project(":shared").projectDir = File("shared")
include(":androidApp")
