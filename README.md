# omhome

A first pass at implementing a personal website using clojure. This implementation is a static site suitable for hosting on GitHub Pages and similar services offering static resource hosting. Key building blocks are the Hiccup, Stasis and Optimus Clojure libraries, and the Tufte CSS stylesheet. More on these choices below.

## Installation

Download from [GitHub](https://github.com/OliverM/omhome), if you like.

## Usage

TBD

## Examples

The main (and potentially only) example should be visible at http://olivermooney.com

### Bugs

Is the current lack of functionality a bug?

### Done
- Follow the steps in <http://cjohansen.no/building-static-sites-in-clojure-with-stasis> as a starting point
- Setup a basic implementation using [Tufte CSS](https://edwardtufte.github.io/tufte-css/) as a neat CSS-only starting point that doesn't interfere by pulling in libraries like jQuery.
- Work up a Hiccup-based workflow that uses inline functions to generate the awkward markup for things like sidenotes and margin notes, which are too baroque to implement comfortably in straight HTML
- Integrate markdown-style replacement of apostrophes with smart quotes, prime marks, ellipses etc as appropriate
- Integrate code-presentation CSS a la Cygments

### To Do
- Customise Tufte CSS:
    - Use custom fonts & colours
    - tweak the CSS to match the new font metrics, and (most awkwardly?) integrate a proper baseline system to for visual consistency/pleasure
    - Add optical margins a la [Typeset](https://github.com/davidmerfield/Typeset) for ever-closer CSS perfection
    - patch unstyled gaps:
      - unordered (all?) list items have poor leading
- Generate index-creation functions using Enlive
  - Build xml feeds for each topic/keyword
- Integration (on those index pages & the post template):
  - twitter 
  - github activity
  - linkedin...? 
- After all that, time for some fun stuff!
  - Integrate Garden to allow for custom per-post layouts
  - Look at how to create & include per-post clojurescript libraries, to present & play with some interesting tech there (e.g. Quil & more)

## License

Copyright © 2014-2016 Oliver Mooney. Apparently it really has taken me that long to get this far.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
