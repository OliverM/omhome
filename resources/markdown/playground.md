<article>
  <section>
#Single hashes give you H1s, look
<p class=subtitle>Adding a subtitle as a p tag with classes.</p>

##Section heading, which resolves to a H2
Some body copy, just to add some shape to this shapeless morass
###A low level, H3 heading.
Some more body. Mmmm, body.

> Blockquote time! But how to apply those pesky blockquotes? Can I just type endlessly after opening with a single one? With soft-wraps in my editor handling it the convenient wrapping of same? I can!
<footer>Quote attribution text goes here</footer>

It's not immediately clear if you should wrap paragraphs in p tags, or if individual sentences are enough for the Tufte CSS. Do I have to delve through it by hand? Probably.

<span class="newthought">One of those nice</span> introductory sentences.
<label for="sn-demo" class="margin-toggle sidenote-number"></label><input type="checkbox" id="sn-demo" class="margin-toggle"/><span class="sidenote">One of those classy Tufte sidenotes.</span>
 Continuing on, I'm not enjoying the mangling of the HTML by the Markdown processor, but perhpas there's a case to be made for a Markdownesque format catering just for the Tufte CSS?

On the other  hand, "pretty quotes!"
</section></article>

<article> #A second article in the same document.
<p class=subtitle>It'd be good to have a custom character for subtitles. An opening underscore?</p> 
Those article tags aren't being properly applied *at all*. </article>

So, Markdown will let you insert HTML block tags like article, but you can only insert HTML inside those blocks, not Markdown (without some custom extensions some processors offer). So modern HTML5 structure, which CSS libraries like TufteCSS seem to require, appears to be flat-out out.

And, thinking about it, it's hard to see how Markdown *could* support them, given that they are block tags, so need an opening *and a closing* tag to indicate their domain. Markdown seems to infer domain from the content in various places, but purely structural block tags, with no dedicated content (only content contained in child block tags) would seem hard to specify (hence why tables were only supported by inserting HTML originally, or by using the ascii layout of MultiMarkdown, which of course has an ending delimiter or a convention that can act as such).
