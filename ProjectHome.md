# Introduction #

The WebCatch-Tag is a JSP-tag to include a part of HTML-code of an external website. With this tag you will be able to include e.g. a footer or header from another webserver.

Example: extract the footer-tag and use it in your own jsp-file
```
<html>
  <body>
    <div id="main">
      This is the main part
    </div>
    <div id="footer" class="column span">
      Created at 12.01.2013 from M.Person
    </div>
  </body>
</html>
```

## Features ##

  * Caching of HTML-code for x seconds before reloading
  * Select part of HTML-code by HTML-tag, attribute and its value
  * Includes HTML-code-parts from other websites build with e.g. php, .net, asp, html, ...
  * Implement own decorators to manipulate HTML-code


## Getting started ##

  * Download jar-file and add into your projects lib-directory
  * Add taglib in your jsp-file
  * Add WebCatch-Tag in jsp-file where to include external HTML-content

For more details goto [Installation](Installation.md)