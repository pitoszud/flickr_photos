# YBS Photo Flickr test

## About this project
Showcase a simple photo search app using Flickr API (details in the separate spec file)

## What this project is not or does not have (yet)

- No Paging3 library - the default loading page is **1** with **30** photos per page.
- There is no adaptive layout, (works in both horizontal and vertical modes).
- No custom theming (but supports light and dark mode).
- There are no back-end services or remote config in the app.
- There are no Google play-services

## How to run this app

In the **local. properties** file specify you Flickr api key e.g **FLICKR_API_KEY=123456789** and rebuild the app.
Alternatively, you can enter you api key in the network-call URL in the PhotoRemoteDataSource.

## Third party libraries

- **io.ktor:ktor-client** - used for network calls.
- **com.google.truth** - used in unit tests for assertions.
- **io.coil-kt:coil** - used to load network images asynchronously.
- **io.mockk:mockk** - used for mocking in tests.
- **org.robolectric** - used for running android tests.

## Caching strategy

- The first default search will clear the local cache and fetch new photos from the api, then it will upsert all photos.
- The second default search or the same query will fetch the photos from the local database, avoiding additional network call.
- A new search will fetch new photos from the API and clear the previous cache.
- If the existing cached photos are more than one day old, the query will clear the cache and fetch new photos from the API.

## Architecture
A clean architecture with MVVM pattern is used in this project. The project is divided into four main layers:
1. **Network** - contains the network calls and the data models.
2. **Data** - contains the repository, data sources, and the database.
3. **Database** - contains the database and the DAOs.
4. **Presentation** - contains the view models and the UI components.

### Helper modules
- **core** - contains a common module for the app.
- **model** - contains the data models for the app.
- **testing** - contains the test utilities for the app.

### Feature modules
- **photos** - contains the photo search feature.


## How the project is updated?

You can submit a standard pull request on **Develop** branch e.g **my_feature_branch -> Develop**. This will run all unit tests in the GitHub action CI pipeline.


## Work in Progress

- database tests
 
