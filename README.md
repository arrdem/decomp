# Decomp

Decomp, short for decompose, is a Clojure tool for translating HTML into the
equivalent [Hiccup](https://github.com/weavejester/hiccup) AST. It was
developed for refactoring raw or static html easily into the Ring web
development stack.

The Hiccup formatting language allows the description of HTML syntax trees in
a Lisp-like prefix notation based on Clojure's Vector literal denoted `[]`. In
Hiccup one may say `[:a {:class "bar" :href "/"} "go home!" ]`, which renders
to the equivalent html `<a class="bar" href="/">go home!</a>`. As you can see
this is a fairly regular translation and the Hiccup tool makes it easy to go 
from Clojure to HTML. Decomp decomposes the expanded html into the 
Huccup-equivalent vector stack, completing the round trip.

Decomp was hacked in a three hour sprint in one evening and is _not_ ready for
use in production code. For serious HTML parsing, look to 
[clj-tagsoup](https://github.com/nathell/clj-tagsoup) for the time being as
many core HTML features are not supported by this toolkit, see 
[Limitations](#-limitations-).

## Usage

```clojure
> (use 'me.arrdem.decomp.core)

;; process-string wraps the lexer and parser in one easy function
> (pprint (process-string
            "<foo a=\"b\"> this
               <!-- ignored -->
               <a href=\"/bar\">
                 goes home
               </a>
               as does
               <a href=\"/\">
                 this!
               </a>
             </foo>"))
[[:foo
    {:a "b"}
        "this"
    [:a {:href "/bar"} "goes home"]
    "as does"
    [:a {:href "/"} "this!"]]]
nil

;; Decomp supports arbitrary properties and tags...
> (process-string "<baz bung=\"1\" blarrrrrrrrgh=\"2\"> </baz>")
[[:baz {:blarrrrrrrrgh "2", :bung "1"}]]
```

Decomp can also be used as a standalone tool able to translate files or standard
input from html to hiccup. The standalone jar can be invoked as
`$ java -jar decomp.jar foo.html` just as one would expect. Multiple file
arguments are supported, and in the absence of file arguments decomp will
attempt to read & process HTML from standard input.

<h2 id="limitations"> Limitations </h2>
This lexer/parser pair is designed to consume the sort of HTML that Hiccup is
used to produce. This means text, links, formatted elements but not Javascript,
or CSS script elements.

- Top-level comments and text will break the parser
- Unbalanced open and close tokens will also kill the parser
- Parser does not do error checking, </foo> can close <bar>

## Todo
- Any sort of error handling or recovery
- Support for optionally terminated tokens such as the `<li>` tag
- Support for self-terminating tokens such as `<script href=... />` and `<br />`

## Get Decomp

### Leiningen:
```Clojure
[me.arrdem.decomp "0.1.3"]
```

### Standalone:
[Standalone Jar](https://raw.github.com/arrdem/decomp/master/decomp.jar)

## License

Copyright © 2013 Reid "arrdem" McKenzie

Distributed under the Eclipse Public License, the same as Clojure.
