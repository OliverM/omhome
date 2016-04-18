<article>
    <h1>Applying the CSS in straight HTML</h1>
    <p class="subtitle">Is a decent HTML editor enough to make markdown unneccessary?</p>
    <section>
        <p><span class="newthought">It does seem that</span> I can readily remember the classes encountered so far, and apply them as desired. As long as I remember to apply my own p tags, something the markdown processor does get right (mostly).<label for="sn-demo" class="margin-toggle sidenote-number"></label><input type="checkbox" id="sn-demo" class="margin-toggle"/><span class="sidenote">In which I try to add a sidenote.</span>
        </p>
        <p>And, of course, embedding HTML directly is the default mode, so the markdown transformations that I've no insight into are sidestepped. </p>
        <p>Here is some HTML:</p>
        <pre class="code"><code class="html">&lt;p>&lt;/p></code></pre>
        <p>I am largely typing to experiment with running this whole shebang through Spacemacs and its Clojure layer - and all its assorted changes. Why am I doing this to myself now? The facility of it all is just so tempting, but struggling up the hill to get there is... long.</p> </section>
    <section><p>
        Do HTML code snippets (not those specified in markdown) get marked up or left as is?
        <pre class="code"><code class="clj">
    (defn colour-test [some-param]
    "A doc string for an imaginary function."
    (when some-param "Colour me good!"))
    </code></pre></p></section></article>
