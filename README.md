# omhome

A personal website, using [Luminus](http://www.luminusweb.net) as a starting point.

**TODO:**

1. Migrate from Luminus' default starting point of Bootstrap to [PureCSS](http://purecss.io). Bootstrap comes with jQuery which'll interfere with other content relying on [SproutCore](http://www.sproutcore.com), [Facebook React](http://facebook.github.io/react/) or (to a far lesser extent) [D3](http://d3js.org)
2. Drop Selmer and migrate HTML templating to [Enlive](https://github.com/cgrand/enlive)
3. Develop a miminal PureCSS theme
4. Deploy to host

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2014 Oliver Mooney
