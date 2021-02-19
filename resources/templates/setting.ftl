<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="data" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>

<!-- Page Header -->
<div class="header_image clearfix" style="padding: 2px 15px;color: #f2f2f2;background-color: #292929">
    <a href="/"><img src="/static/redhatlogo.svg" width="180" height="35" alt=""/></a>
    <h2 style = "vertical-align:middle;">CEEnter</h2>
    <p style="float: right">version ${version}</p>
</div>
<!-- Navigation bar START -->
<div class="sidebar">
    <a href="/ordertower"> Order/Tower </a>
    <a href="/order"> Order/JSON </a>
    <a href="/jsonConfig"> JSON Config </a>
    <a class="active" href="/setting"> Settings </a>
</div>
<!-- Navigation bar END -->

<!-- Content START -->
<div class="content">
    <h1>Settings</h1>
    <hr>
    <form class="form-style-6" action="/savesetting" method="post" style="max-width: 800px;">
        <table>
            <#list data as item>
            <tr>
                <td style="width:30%">${item.parameter}</td>
                <td style="width:70%">
                <label><input type="text" name="${item.parameter}" value="${item.value}"/></label></td>
            </tr>
            </#list>
        </table>
        <input type="submit" value="Save Settings">
    </form>
</div>
<!-- Content END -->

</body>
</html>
