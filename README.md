# ServerStorage

> Minimal FTP server database

## Description

ServerStorage is an example of a minimalistic FTP server database class, using [SQLiteOpenHelper](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper) for database management.

ServerStorage is used in [AndPlug](https://play.google.com/store/apps/details?id=com.omicronapplications.andplug) music player application for Android devices.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Testing](#testing)
- [Usage](#usage)
- [Example](#example)
- [Credits](#credits)
- [Release History](#release-history)
- [License](#license)

## Prerequisites

- [Android 4.0.3](https://developer.android.com/about/versions/android-4.0.3) (API Level: 15) or later (`ICE_CREAM_SANDWICH_MR1`)
- [Android Gradle Plugin](https://developer.android.com/studio/releases/gradle-plugin) 7.0.1 or later (`gradle:7.0.1`)

## Installation

1. Check out a local copy of ServerStorage repository
2. Build library with Gradle, using Android Studio or directly from the command line

## Testing

ServerStorage includes a set of instrumented unit tests.

### Instrumented tests

Located under `ftplib/src/androidTest`.

These tests are run on a hardware device or emulator, and verifies correct operation of the `ServerStorage` implementation.

## Usage

ServerStorage is controlled through the following class:
- `ServerStorage` - FTP server database class 

## Example

Create new `ServerStorage` instance, including new database:

```
import com.omicronapplications.serverlib.ServerStorage;

ServerStorage storage = new ServerStorage(getApplicationContext(), "ftp", 1);
```

Add FTP server to database:

```
FTPServer server = new FTPServer("server.com", 22, "user", "password", "/");
storage.addServer(server);
```

Update FTP server name in database:

```
List<FTPServer> list = storage.getServers();
FTPServer server = list.get(0);
server.setHost("other.com");
storage.editServer(server);
```

Delete FTP server from database:

```
storage.deleteServer(server);
```

Delete server database completely:

```
storage.deleteStorage();
```

## Credits

Copyright (C) 2019-2021 [Fredrik Claesson](https://github.com/omicronapps)

## Release History

- 1.0.0 Initial release
- 1.1.0 Migrated to AndroidX

## License

ServerStorage is licensed under [GNU LESSER GENERAL PUBLIC LICENSE](LICENSE).
