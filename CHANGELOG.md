# Changelog

### v0.16.0
- Query the source model name and image url when getting all articles

### v0.15.7
- Better equals() method for source comparisons
- Add datastore method to remove a source
- Remove final modifier on DataSource for easier mocking

### v0.15.2
- Improve Android Wear 2.0 support

### v0.15.1
- Refactor away from the singleton pattern for the `DataSource`

### v0.14.0
- Add `source` and `category` tables for some future features.

### v0.13.0
- Fix proguard configuration. No app's implementing this library will need to make changes

### v0.12.1
- Fix some permission issues

### v0.12.0
- API for saving articles to apps that support it (See [README.md](README.md))

### v0.11.0
- Improve text selection colors
- Trending API endpoints
- Ability to "prepare" an article on the server, without downloading it to the device
- Fixes for blank images
- Update support library

### v0.10.2
- Crash and bug fixes

### v0.10.0
- New `ArticleIntent.Builder#setTextSize` API
- Allow text selection within a paragraph

### v0.9.1
- Bug fixes for image loading

### v0.9.0
- API to fetch articles on whatever thread it is called from, instead of doing it asynchronously

### v0.8.0
- API to preload articles and images
- Handle HTML lists

### v0.7.0
- Elastic Drag-to-Dismiss

### v0.6.0
- Standalone image viewer

### v0.5.0
- Initial Release
