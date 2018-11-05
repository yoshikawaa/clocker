# clocker

Simple CountDown Application using [FlipClock.js](http://flipclockjs.com/).

## Features

CountDown Business Time.

## How to use

Build and run application.

```console
$ mvn clean spring-boot:run
```

## How to configure

Configure with properties.

| name                     | description                             | format                         |
|--------------------------|-----------------------------------------|--------------------------------|
| `clocker.holidays-file`  | CSV File of Holidays.                   | Spring `ResourceLoader` format |
| `clocker.limit-date`     | Target Date of Countdown.               | `ISO_LOCAL_DATE`               |
| `clocker.business.start` | Business Time Start. (Enable CountDown) | `ISO_LOCAL_TIME`               |
| `clocker.business.end`   | Business Time End. (Disable CountDown)  | `ISO_LOCAL_TIME`               |
| `clocker.interval.start` | Interval Start. (Disable CountDown)     | `ISO_LOCAL_TIME`               |
| `clocker.interval.end`   | Interval End. (Enable CountDown)        | `ISO_LOCAL_TIME`               |







