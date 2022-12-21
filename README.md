# Documentation

Please see detailed documentation below or in the following link:

[https://docs.ad4tech.net/sdk-ads-android.html](https://docs.ad4tech.net/sdk-ads-android.html)

# Advertising Demo app

Go to [advertising-demo-app](advertising-demo-app/) folder, here in the repo.

# Advertising SDK Android

Android Ads SDK (hereinafter SDK) is designed for monetization of advertising inventory in Android applications. As part of SDK
are included:

- Advertising SDK - directly to requests and displays ads in the app
- [Tracking SDK](https://github.com/solutionarchitectstech/android_tracker_sdk_release) - to record targeted user actions in the application.

::: warning Before you start

You should be aware of the following parameters required for initialization and use SDK.
Ask the SDK team to give them to you.

- partnerId - partner ID
  For example: `123`
- baseUrl - URL API of advertising system
  For example: `"https://my.server.com/"`
- trackingBaseUrl - URL API of system for recording targeted user actions.
  For example: `"https://track.server.com"`
  :::

**Ads SDK version**: 0.1.4

**Demo**: [advertising-demo-app](advertising-demo-app/)

**Stable relase**: [solutionarchitectstech:mobile_sdk_release](https://github.com/solutionarchitectstech/mobile_sdk_release)

**Requirements**

- Kotlin version >= 1.6.20
- Android 5.0+ (API Level >= 21)

## Installation

- Import Gradle dependency

  To use Android Ads SDK you need to install a dependency in your application’s Gradle file:

```-vue
dependencies {
    //... other dependencies
    implementation 'com.github.solutionarchitectstech:mobile_sdk_release:0.1.4'

    //... other dependencies
}
```

## SDK initialisation

In order to initialize the SDK and create of ad placements by the developer should call `TechAdvetrising.init()` method at the time of the application launch or at the moment of user authorization.

```kotlin
TechAdvertising.init(
    context: Context,
    storeUrl: String,
    partnerId: Int,
    uid: String,
    baseUrl: String,
    trackingBaseUrl: String,
    debugMode: Boolean = false,
    headers: Map<String, () -> String>? = null
)
```

- `context` - (required) class object [Context](https://developer.android.com/reference/android/content/Context),
  global application context. Provided [Android SDK](https://developer.android.com/reference).
- `storeUrl` - (required) link to the app in the app store.
  For example: `"https://play.google.com/store/apps/details?id=com.myapp"`.
- `partnerId` - (required) the partner ID given to you (see above).
- `uid` - unique user ID. The SDK itself **does not** request standard identifiers users in the application. An identifier can be a hash of an email or a phone number specified user upon registration.
  For example: `"bfd0620a5017d1362431aea6c1d6e504"`.
- `baseUrl` - (required) URL of the advertising system API given to you (see above).
  For example: `"https://my.server.com/"`.
- `trackingBaseUrl` - (required) given to you API URL of registration system for the target user actions (see above).
  For example: `"https://track.server.com/"`.
- `debugMode` - (optional, default false) flag that enables logging of debug messages in application
- `headers` - (optional, default null) dictionary that forms specific HTTP headers.


```kotlin
TechAdvertising.init(
    context = applicationContext,
    storeUrl = "https://play.google.com/store/apps/details?id=com.myapp",
    publisherId = 123,
    uid = "bfd0620a5017d1362431aea6c1d6e504",
    baseUrl = "https://my.server.com",
    trackingBaseUrl = "https://track.server.com",
    debugMode = false,
    headers = mapOf("Authorization" to {
        "Bearer Test"
    })
)
```

### Interaction with Tracking API

Ads SDK also includes [Tracking SDK](https://github.com/solutionarchitectstech/android_tracker_sdk_release). The Tracker is initialized during the initialization of the advertising SDK.
The tracker is accessed via `TechAdvetrising`:

```kotlin
TechAdvertising.tracker.event(event)
```

More details about the methods and events of the tracker are written in the [Tracking SDK](https://github.com/solutionarchitectstech/android_tracker_sdk_release).

## Display banner

![banner-view](advertising-demo-app/README_md/sdk-ads-android.gif)

To request and display banner ads, call the method  `BannerView.load()`:

```kotlin
banner.load(
    placementId: String,
    sizes: List<Size>,
    timeout: Int? = null,
    refresh: Int? = null,
    closeButtonType: CloseButtonType = CloseButtonType.Visible,
    customParams: Map<String, String>? = null,
    listener: TechAdvertisingListener? = null
)
```

- `placementId` - (required) ID of the ad slot. For example: `456`.
- `sizes` - (required) list of valid [sizes](#size) for the banner. For example: `listOf(Size(300,250))`.
- `timeout` - (optional) - maximum time in seconds before closing advertisement.
- `refresh` - (optional) - time in seconds in which sending new request for getting advertisement.
- `closeButtonType` - (optional, default: `CloseButtonType.Visible`) type of button from enum.
- `customParams` - (optional) `Map<String, String>` custom parameters to add to banner request. Eg: `customParams = mapOf("example" to "value", "example2" to "value2")`.
- `listener` - (optional) banner loading event handler function that accepts an event of one of the following types as input:
    - `BannerLoadDataSuccess` - a request to the ad server returned a link to the content;
    - `BannerLoadDataFail` - error while requesting the ad server;
    - `BannerLoadContentSuccess` - creative uploaded successfully;
    - `BannerLoadContentFail` - error loading creative;
    - `BannerCloseButtonClick` - the user closed the ad viewing screen.

### Example with xml-layout

```kotlin
import tech.solutionarchitects.advertisingsdk.listener.*

banner.load(
    placementId = "456",
    sizes = listOf(Size(width = 300, height = 250), Size(width = 300, height = 300))
) { event ->
    when (event) {
      is BannerLoadDataSuccess -> {
        println("BannerLoadDataSuccess: ${event.placementId}")
      }
      is BannerLoadDataFail -> {
        println("BannerLoadDataFail: ${event.throwable}")
      }
      is BannerLoadContentSuccess -> {
        println("BannerLoadContentSuccess: ${event.placementId}")
      }
      is BannerLoadContentFail -> {
        println("BannerLoadContentFail: ${event.throwable}")
      }
      is BannerCloseButtonClick -> {
        println("BannerCloseButtonClick: ${event.placementId}")
        finish()
      }
      else -> {}
    }
}
```

### Example of programmatically creating placements

```kotlin
val bannerView = BannerView(applicationContext)
val layout = findViewById<ConstraintLayout>(R.id.mainLayout)
layout.addView(bannerView)

bannerView.layoutParams = ViewGroup.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT
)

bannerView.load(
    placementId = "456",
    sizes = listOf(Size(width = 300, height = 250)),
    // customParams = mapOf("example" to "value", "example2" to "value2")    
) { event ->
    when (event) {
      is BannerLoadDataSuccess -> {
        println("BannerLoadDataSuccess: ${event.placementId}")
      }
      is BannerLoadDataFail -> {
        println("BannerLoadDataFail: ${event.throwable}")
      }
      is BannerLoadContentSuccess -> {
        println("BannerLoadContentSuccess: ${event.placementId}")
      }
      is BannerLoadContentFail -> {
        println("BannerLoadContentFail: ${event.throwable}")
      }
      is BannerCloseButtonClick -> {
        println("BannerCloseButtonClick: ${event.placementId}")
        finish()
      }
      else -> {}
    }
}
```

## Helper classes

### Size

Object describing the size of an ad placement. When initialized takes two integers corresponding to the width and height
of an ad placemnt in pixels.

```kotlin
class Size(val width: Int, val height: Int) {...}
```

- `width` - Width in pixels. For example: `300`
- `height` - Height in pixels. For example: `250`


```kotlin
Size(width = 300, height = 250)
```