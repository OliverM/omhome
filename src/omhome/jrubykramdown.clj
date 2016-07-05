(ns omhome.jrubykramdown
  "A namespace to convert KramDown markdown text to HTML, by invoking JRuby. Requires KramDown to be installed on the local machine (e.g. via `jruby -S gem install kramdown'.) Based in large part on https://gist.github.com/plexus/07f6831ee3963549207dd0309e10a464"
  (:import [org.jruby Ruby]
           [org.jruby.runtime ThreadContext]
           [org.jruby.runtime.builtin IRubyObject]
           [org.jruby.javasupport JavaUtil]))

(defonce ruby (memoize #(Ruby/newInstance)))

(defn- java->ruby [obj]
  (JavaUtil/convertJavaToRuby (ruby) obj))

(defn- ruby-eval
  "Evaluate supplied strings as Ruby expressions in the current Ruby interpreter. Returns the execution context of the interpreter after the final expression has been evaluated."
  [& ss]
  (last (for [s ss] (.evalScriptlet (ruby) s))))

(defn call-proc
  "Invoke the current Ruby execution context with arguments converted from Java into JRuby objects"
  [this & args]
  (.call this (.getCurrentContext (ruby))
         (into-array IRubyObject (map java->ruby args))))

(def ^:private convert*
  "Prepare a Ruby execution context with KramDown included and waiting for text input."
  (memoize
   #(ruby-eval "require 'kramdown'"
             "-> str { Kramdown::Document.new(str).to_html }")))

(defn convert
  "Convert KramDown text to HTML."
  [s]
  (.asJavaString (call-proc (convert*) s)))
