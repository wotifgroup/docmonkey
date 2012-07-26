<html>
<head><title>${export.title}</title></head>
<body>
<h1>Hello, ${export.name?html}!</h1>
<ul>
<#list export.links as item>
<li><img src="${export.dir}/${item?html}"/></li>
</#list>
</ul>
</body>
</html>