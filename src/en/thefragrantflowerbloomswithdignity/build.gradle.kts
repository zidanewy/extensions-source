import io.github.keiyoushi.gradle.api.ContentWarning
plugins {
    alias(kei.plugins.extension)
}

keiyoushi {
    contentWarning = ContentWarning.SAFE
    name = "The Fragrant Flower Blooms with Dignity"
    versionCode = 1
    libVersion = "1.6"

    source {
        lang = "en"
        baseUrl {
            mirrors("https://thefragrantflowerbloomswithdignity.com")
        }
    }
}
