# Setup Instructions for The Fragrant Flower Blooms with Dignity

## Easiest: build the APK on GitHub (no local tools)
If this project lives in the mihon-ext-builder repository on GitHub:
open the **Actions** tab, pick **"Build Extension APK"**, click
**Run workflow**, paste the site URL, and download the finished APK
from the run's **Artifacts** when it completes.

---

## Build it yourself

### 1. Get the extensions-source repository
```
git clone https://github.com/keiyoushi/extensions-source.git
cd extensions-source
```

### 2. Copy this extension into the repo
```
cp -r "generated\en.thefragrantflowerbloomswithdignity" src/en/thefragrantflowerbloomswithdignity/
```

### 3. Build the APK
```
./gradlew src:en:thefragrantflowerbloomswithdignity:assembleDebug
```
The APK will be at:
`src/en/thefragrantflowerbloomswithdignity/build/outputs/apk/debug/`

## 4. Install on device
```
adb install -r path/to/extension.apk
```
Or sideload via Mihon Settings → Browse → Add extension repo.

## 5. Key files to customise
- **TheFragrantFlowerBloomsWithDignity.kt** — main source class; fill in `TODO:` selectors
- **build.gradle** — bump `extVersionCode` each release
- **AndroidManifest.xml** — update domain if the site URL changes

## Site analysis notes
- Cloudflare present but passable — extension may need CloudflareInterceptor

## Detected type: custom
Template used : http_source
Cloudflare    : YES — CloudflareInterceptor is already wired in
