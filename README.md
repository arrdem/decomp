# Decomp

Decomp, short for decompose, is a Clojure tool for translating HTML into the
equivalent [Hiccup](https://github.com/weavejester/hiccup) AST. It was
developed for refactoring raw or static html easily into the standard
clojure, ring, compojure, hiccup web development stack.

The Hiccup formatting language allows the description of HTML syntax trees in
a Lisp-like prefix notation based on Clojure's Vector literal denoted `[]`. In
Hiccup one may say `[:a {class "bar" :href "/"} "go home!"" ]`, which renders
to the equivalent html `<a href="/" class="bar"> go home! </a>`. As you can see
this is a fairly regular translation and the Hiccup tool make it easy to go from
Clojure to HTML. Decomp decomposes the expanded html into the Huccup-equivalent
vector stack, completing the round trip.

## Example

    > (use 'me.arrdem.decomp.core)

    ;; process-string wraps the application of the lexer, parser and pprinter in one easy function
    > (process-string "<foo a=\"b\"> this <!-- ignored --> <a href=\"/bar\"> goes home </a> as does <a href=\"/\"> this! </a> </foo>")
    [[:foo
        {:a "b"}
            "this"
            [:a {:href "/bar"} "goes home"]
            "as does"
            [:a {:href "/"} "this!"]]]
    nil

    ;; Decomp supports arbitrary properties...
    > (process-string "<baz bung=\"1\" blarrrrrrrrgh=\"2\"> </baz>")
    [[:baz {:blarrrrrrrrgh "2", :bung "1"}]]
    nil

## Limitations

 - Top-level comments for instance will break the parser.
 - Unbalanced open and close tokens will also kill the parser
 - Javascript (due to semicolons and {}) will likely break the parser or at least behave strangely
 - Inline CSS should work but is iffy

## License

Copyright © 2013 Reid "arrdem" McKenzie

Distributed under the Eclipse Public License, the same as Clojure.
