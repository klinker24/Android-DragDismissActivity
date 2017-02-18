![feature graphic](artwork/sample.png)

# Android Drag Dismiss Activity

Another implementation of the drag-to-dismiss `Activity` pattern. This one is inspired by Nick Butcher's [Plaid](https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/ui/widget/ElasticDragDismissFrameLayout.java) implementation.

The project has a simple API and is a pretty powerful and beautiful implementation of the pattern. This library is used in some of my apps ([Talon for Twitter](https://play.google.com/store/apps/details?id=com.klinker.android.twitter_l) and [Pulse SMS](https://play.google.com/store/apps/details?id=xyz.klinker.messenger)). It has been abstracted from Jacob Klinker and I's [article-android](https://github.com/klinker41/article-android/) library, which is an awesome readability style in-app web browser.

This library provides an elastic layout that can dismiss a `RecyclerView` (at the top and the bottom of the list), as well as regular `Activity` content, that will be wrapped in `ScrollView`.

## Including It In Your Project

This project was designed to be very easy to implement into your existing `Activities`.

To include it in your project, add this to your module's `build.gradle` file:

```groovy
dependencies {
	...
	compile 'com.klinkerapps:drag-dismiss-activity:0.0.1-SNAPSHOT'
}
```

#### Using it with any layout

#### Using it with a RecyclerView

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/klinker24/Android-DragDismissActivity/pulls). Features can be requested using [issues](https://github.com/klinker24/Android-DragDismissActivity/issues). All code, comments, and critiques are greatly appreciated.

## Changelog

The full changelog for the library can be found [here](https://github.com/klinker24/Android-DragDismissActivity/blob/master/CHANGELOG.md).

## License

    Copyright 2017 Luke Klinker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
