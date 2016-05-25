[:article
 [:h1 "Testing Hiccup post creation"]
 [:section
  [:p
   [:span.newthought "It seems clear that "]
   "Hiccup allows you to create HTML succintly, and the editing experience for Clojure manages"
   (make-side-note "Testing side-note creation." "sidenote-1")
   " the data structure creation and manipulation for you. Even better, the same ability to manipulate CSS is availabe via the Garden library. Inline functions may not be the most intuitive means of generating complex HTML or CSS, but they allow programmers"
   (make-margin-note "Some subset of programmers, at least. Hiccup & Garden leverage Clojure datastructures, which are not immediately familiar to your typical programmer, but are easy to pick up." "marginnote-1")" to leverage their Clojure (or other programming language) insight in an intuitive way."]]
 [:section
  [:h2 "Code formatting"]
  [:p "Tufte CSS specifies that code sections should be wrapped in "[:code.html "pre"]" tags. Meanwhile, the Stasis setup blog I've been following has an inner
 code element with the language of the code set as a class on that code element (using github's three-letter acronyms, as it's the same Pygments library they
 use). "]
  [:p "Trying to construct all of this in Hiccup gives the following:"]
  [:pre.code
   [:code.clj
    "(defn a-test-fn
  \"Sample doc string for this test function.\"
  [param1 param2]
  (map param1 param2))"]]
  [:p "And really the only problem with this is the manual indenting the
 standard setup of Spacemacs requires, which does point towards a helper
 function. But how to implement?"]
  [:p "Pretty easily, it turns out: just have the function wrap the supplied text in the expected html tags, as hiccup structures."]
  (code-block "clj"
              "(defn another-test-fn
  \"Sample doc string for this second test function...\"
  [param1 param2]
  (apply param1 param2))")
  [:p "That seems to work, but still needs manual indenting."]
  [:p "It looks like auto code highlighting is still v. problematic online: fixed-width, wraps in horizontal scroller automatically (which is horrible on mobile and not that great on desktop), no indenting, and fairly opaque HTML." ]
  [:p "So, code-highlighting is implemented here by firing up Enlive, grovelling through the HTML for the relevant HTML elements, then spinnin Clygments up to process them. Surely we could adapt this for nice quotes, ellipses & the rest? Here's some tests:"
   [:ul [:li "Smart quotes: \"Testing quotes.\"  "]
    [:li "Ellipses: ... and ..."]
    [:li "Triple primes: '''"]
    [:li "Double primes: ''"]
    [:li "Smart single quotes: 'Boo!' and in a quoted title: \"Joe's\". Contracting a year: '96, and standalone: ' (making sure not to replace triple-primes with three single quotes)."]
    [:li "Dashes: en-dash -- and em-dash ---"]
    [:li "Implementation doesn't suffer from smartquotesjs's transformation of link elements: " [:a {:href "http://smartquotesjs.com"} "Does it?" ]]
    [:li "Finally, these transforms should be excluded from code blocks: " (code-block "clj" "(defn smartypants-test
  \"'96 was Joe's year... 24'''10'' \n-- or more! 'Boo!' \"
  [param & more]
  (apply print param1 more))")]]]]]
