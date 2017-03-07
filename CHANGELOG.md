# Changelog

### v1.3.0

* Option to set the drag elasticity on the `DragDismissIntentBuilder".
`
### v1.2.3

* Improve the swipe to dismiss distance

### v1.2.2

* Improve scrolling on the `DragDismissActivity` by removing the `AppBarLayout` and handing its functionality myself

### v1.2.1

* Don't have `Activity#onCreate` as `final`
* Add `savedInstanceState` to the `Activity` initializers

### v1.2.0

* Add option to have tablet's use the full screen for the `DragDismissActivities`

### v1.1.2

* Ensure that the `Toolbar` color is correct on the `Activities` with `NestedScrollView`

### v1.1.1

* Change `DragDismissBundleBuilder` to `DragDismissIntentBuilder`

### v1.1.0

* Add an API to access a `ProgressBar` for loading data

### v1.0.0

* Initial Release
  * Drag down and up to dismiss an *Activity*
  * Works with `RecyclerView` and normal `Activity` layouts
  * Custom color support
  * Theme support (light, dark, and day/night)
  * Hide the toolbar