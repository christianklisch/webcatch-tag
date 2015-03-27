# What is the WebCatch-tag #

The WebCatch-tag is a jsp-tag to include parts of an external website (e.g. footer). In case of a change on the external website the selected website-part will be loaded by your webserver and the extracted HTML-code is included into your jsp-file.


## Include which part? ##

You can select by html-tag-attributes, which part of the external website you want to include. The following example is the html-code of a footer:

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


## How to use in jsp-file ##

After [Installation](Installation.md) add at the beginning of your jsp-file:
```
<%@ taglib uri="http://www.christian-klisch.de" prefix="ck"%>
```

Add in your jsp-code the WebCatch-tag:
```
<ck:webcatch source="http://www.christian-klisch.de" htmltag="div" attribute="id" value="footer" />
```

This will include:
```
    <div id="footer" class="column span">
      Created at 12.01.2013 from M.Person
    </div>
```

## Manipulate the content ##
If you need to manipulate the content (e.g. change server-url in link), you have to implement the WebCatchDecorator.