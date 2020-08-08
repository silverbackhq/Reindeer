<a href="http://www.reactivemanifesto.org/"> <img style="border: 0; position: fixed; left: 0; top:0; z-index: 9000" src="https://d379ifj7s9wntv.cloudfront.net/reactivemanifesto/images/ribbons/we-are-reactive-black-left.png"> </a>
<p align="center">
    <img alt="Reindeer Logo" src="https://raw.githubusercontent.com/silverbackhq/Reindeer/master/images/logo.png" height="160" />
    <h3 align="center">Reindeer</h3>
    <p align="center">Reactive Applications Boilerplate in Java.</p>
    <p align="center">
        <a href="https://travis-ci.org/silverbackhq/Reindeer"><img src="https://travis-ci.org/silverbackhq/Reindeer.svg?branch=master"></a>
        <a href="https://github.com/silverbackhq/Reindeer/releases"><img src="https://img.shields.io/badge/Version-1.1.0-blue.svg"></a>
        <a href="https://hub.docker.com/r/clivern/reindeer"><img src="https://img.shields.io/badge/Docker-Latest-orange"></a>
        <a href="https://github.com/silverbackhq/Reindeer/blob/master/LICENSE"><img src="https://img.shields.io/badge/LICENSE-Apache_2.0-orange.svg"></a>
    </p>
</p>

This project is mainly for personal & testing purposes but you can simply fork or use it as a template.

## Documentation


### Development:

Reindeer built on a top of [vert.x](https://vertx.io/) and uses [gradle](https://gradle.org/) as a build tool. In order to get started, you need to clone the repository and create a config file.

```bash
$ git clone https://github.com/silverbackhq/Reindeer.git
$ cp .env.example .env
```

Then define your custom configs

```env
APP_NAME=Reindeer
APP_KEY=
APP_DEBUG=true
APP_URL=http://localhost
APP_PORT=8000

APP_AUTH_TOKEN=

DB_CONNECTION=h2
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=./storage/db
DB_USERNAME=root
DB_PASSWORD=secret

APP_TIMEZONE=UTC
APP_LANGUAGE=en-us

APP_LOGGING_HANDLERS=console
APP_LOGGING_LEVEL=debug
APP_LOGGING_FORMAT={date} [{level}] {class}.{method}() {message}

REINDEER_LOAD_FROM=config
```

And then migrate & run the application.

```bash
# Provide the path to .env file & Run the app
$ make ARGS="-Denv=/path/to/.env" run
```

Whenever you do changes, please make sure to run the following commands locally:

```bash
# to make sure unit and quality testing passed.
$ make ci

# to format the new code changes.
$ make format

# To list all commands
$ make
```


### Deployment:

You can build and run the jar like this:

```bash
$ make build

$ java -jar build/libs/reindeer-1.1.0-fat.jar --env=/path/to/.env
```


### Deployment with Docker:

To run from docker image:

```bash
$ docker run -d \
    --env APP_PORT=8000 \
    --env DB_CONNECTION=h2 \
    --env DB_HOST=127.0.0.1 \
    --env DB_PORT=3306 \
    --env DB_DATABASE=/app/storage/db \
    --env DB_USERNAME=root \
    --env DB_PASSWORD=secret \
    --name=reindeer \
    --publish 8000:8000 \
    clivern/reindeer:1.1.0
```

Or with docker compose

```bash
$ docker-compose up -d
```


## Versioning

For transparency into our release cycle and in striving to maintain backward compatibility, Reindeer is maintained under the [Semantic Versioning guidelines](https://semver.org/) and release process is predictable and business-friendly.

See the [Releases section of our GitHub project](https://github.com/silverbackhq/reindeer/releases) for changelogs for each release version of Reindeer. It contains summaries of the most noteworthy changes made in each release.


## Bug tracker

If you have any suggestions, bug reports, or annoyances please report them to our issue tracker at https://github.com/silverbackhq/reindeer/issues


## Security Issues

If you discover a security vulnerability within Reindeer, please send an email to [hello@clivern.com](mailto:hello@clivern.com)


## Contributing

We are an open source, community-driven project so please feel free to join us. see the [contributing guidelines](CONTRIBUTING.md) for more details.


## License

© 2019, Silverback. Released under [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

**Reindeer** is authored and maintained by [@Silverbackhq](http://github.com/silverbackhq).
