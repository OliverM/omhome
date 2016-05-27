[:article
 [:h1 "A Programmer's Lament"]
 [:h2 "Complaints about VIM as I Struggle to Master It"]
 [:h3 "~ or ~"]
 [:h1 "Straw Horses & VIM"]
 [:h2 "How They Tell You Editing Sucks, vs. How It Actually Sucks"]
 [:section
  [:p [:span.newthought "I am typing this in " [:a {:href "http://spacemacs.org"} "Spacemacs"]] ", a version of Emacs that's been overhauled to operate like Vim."]
  [:p "I've watched "[:a {:href "https://vimeo.com/53144573"} "Drew Neil's talk on Vim"] " and bought his book, which I'm still working through. I've run through the VimTutor more than once, in Vim and in Spacemacs, and I've used Spacemacs for real work (both client work and to build the static site generator that creates these pages)."
   [:ul
    [:li "The mouse is BAD (not if you've played video games all your life, it's not; first-person shooters are ALL ABOUT hitting arbitrary tiny targets fast, and I've been doing that for a decade or more for fun; and these days the touchpad on laptops is always in the same place, vastly reducing time needed to find & use it). For example, Neil (around 33 minutes into his talk) talks about a notional touch interaction where you've to pick out the opening and closing brackets/tags/quotes of a section of text to highlight them. But who would suggest that?! There's already a great way of selecting the matching closing tag in a text using a mouse: double-click the opening (or closing) tag. IntelliJ does this, and I'm sure others do too. It works " [:emphasis "without"] " you having to even consider the type of tag and finding it's matching symbol on the keyboard. The v-a-t workflow Neil describes is " [:strong "great"] ", but made-up examples of poor alternatives don't sell it."]
    [:li "The cursor keys are BAD because... reasons?"]
    [:li "Macros! Macros are the best. (Story about editing 23MB file here, long line lengths, v-a-t, automating in a macro)"]
    [:li "Going backwards through text or shrinking text selections seems arbitrarily hard"]
    [:li "The God-damned pound symbol (aka why I gave up on Emacs more than once) and how it works properly on the commandline for arbitrary reasons, but other keypresses just don't (forward & back in the repl?)"]
    [:li "You can't contract selections, only expand them (cf. Drew Neil again, c. 47:30). BOOOOO."]
    [:li "Inadvertent deletions: the delete key 'd' is on the home row in command mode, and idly resting your fingers there on a sensitive keyboard, then performing a motion without noticing you've pressed d first, means part of your work can be deleted without realising. This has happened me enough times to be embarrassing."]]]]]

