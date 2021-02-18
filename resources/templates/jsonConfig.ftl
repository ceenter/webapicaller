<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="jsondataURL" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/style.css">
    <link href="/static/jsoneditor/dist/jsoneditor.css" rel="stylesheet" type="text/css">
    <script src="/static/jsoneditor/dist/jsoneditor.js"></script>
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
    <a class="active" href="/jsonConfig"> JSON Config </a>
    <a href="/setting"> Settings </a>
</div>
<!-- Navigation bar END -->

<!-- Content START -->
<div class="content">
    <h1>JSON Config</h1>
    <hr>
    <p>
        <button id="setJSONURL">Read JSON from Git config file</button>
        <button id="getJSON">Display raw JSON</button>
        <button id='submit'>Submit (console.log)</button>
    </p>
    <div id="jsoneditor"></div>

    <script>
        // create the editor
        const container = document.getElementById('jsoneditor')
        const options = {}
        const editor = new JSONEditor(container, options)

        // set json URL
        document.getElementById('setJSONURL').onclick = function () {
            const json = ${jsondataURL?no_esc}
            editor.set(json)
        }

        // get json
        document.getElementById('getJSON').onclick = function () {
            const json = editor.get()
            alert(JSON.stringify(json, null, 2))
        }

        // Hook up the submit button to log to the console
        document.getElementById('submit').addEventListener('click',function() {
            // Get the value from the editor
            console.log(editor.get());
        });

    </script>
</div>
<!-- Content END -->

</body>
</html>
