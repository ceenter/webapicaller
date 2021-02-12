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

<!-- Navigation bar START -->
<div class="header_image clearfix" style="padding: 2px 15px;color: #f2f2f2;background-color: #292929">
    <a href="/"><img src="/static/redhatlogo.svg" width="180" height="35"/></a>
    <h2 style = "vertical-align:middle;">CEEnter</h2>
    <p style="float: right">version ${version}</p>
</div>

<div class="sidebar">
    <a href="/order"> Order/JSON </a>
    <a href="/jsonConfig"> JSON Config </a>
    <a class="active" href="/setting"> Settings </a>
</div>
<!-- Navigation bar END -->

<!-- Content START -->
<div class="content">
    <h1>Settings</h1>
    <hr>
    <form class="form-style-6">
        <input type="checkbox" id="towercall" name="towercall" value="1">
        <label for="towercall"> Call Tower directly</label><br><br>
        <label for="toweradm">ansible-tower Admin:</label><br>
        <input type="text" id="toweradm" name="toweradm"><br>
        <label for="towerpass">ansible-tower Password:</label><br>
        <input type="text" id="towerpass" name="towerpass"><br>
        <input type="submit" value="Save Settings">
    </form>
</div>
<!-- Content END -->

</body>
</html>
