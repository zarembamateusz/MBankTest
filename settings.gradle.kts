pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MBankTestCase"
include(":app")
include(":domain")
include(":core-database")
include(":core-api")
include(":core-ui")
include(":core-utils")
include(":core-data")
include(":core-model")
include(":feature-currency-list")
include(":feature-currency-details")
