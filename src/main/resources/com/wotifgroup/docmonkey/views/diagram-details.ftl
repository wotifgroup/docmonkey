<#-- @ftlvariable name="" type="com.wotifgroup.docmonkey.views.ExportView" -->
<html>
<head><title>${export.title}</title></head>
<body>
<h1>Hello, ${export.name?html}!</h1>
<ul>
<#list export.links as item>
<li><img src="${export.uri}${item?html}"/></li>
</#list>
</ul>
</body>
</html>
