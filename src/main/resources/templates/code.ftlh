<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<div>
    <#if empty>
        <p>No code snippets</p>
    <#--    <#elseif one_snippet>-->
    <#else>
        <#list snippets as snippet>
            <div>
                <#if snippet.isSecretByTime()>
                    <span id="time_restriction">${snippet.getTime()}</span>
                </#if>
                <#if snippet.isSecretByView()>
                    <span id="views_restriction">${snippet.getViews()}</span>
                </#if>
            </div>
            <div>
                <span id="load_date">${snippet.getFormattedDate()}</span>
                <pre id="code_snippet"><code>${snippet.code}</code></pre>
            </div>
        </#list>
    </#if>

    <script>

    </script>
</div>
</body>
</html>