<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="jsondata" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link href="/static/jsoneditor/dist/jsoneditor.css" rel="stylesheet" type="text/css">
    <script src="/static/jsoneditor/dist/jsoneditor.js"></script>
</head>
<body>

<div class="wrapper d-flex align-items-stretch">
    <nav id="sidebar">
        <div class="custom-menu">
            <button type="button" id="sidebarCollapse" class="btn btn-primary">
                <i class="fa fa-bars"></i>
                <span class="sr-only">Toggle Menu</span>
            </button>
        </div>
        <h1><a href="/" class="logo">CEEnter ${version}</a></h1>
        <ul class="list-unstyled components mb-5">
            <li class="active">
                <a href="/dashboard"><span class="fa fa-home mr-3"></span> Dashboard</a>
            </li>
            <li>
                <a href="#"><span class="fa fa-user mr-3"></span> Tower Setup</a>
            </li>
            <li>
                <a href="#"><span class="fa fa-sticky-note mr-3"></span> OpenShift Setup</a>
            </li>
            <li>
                <a href="#"><span class="fa fa-sticky-note mr-3"></span> Logs</a>
            </li>
            <li>
                <a href="/jsonConfig"><span class="fa fa-paper-plane mr-3"></span> JSON Config</a>
            </li>
            <li>
                <a href="#"><span class="fa fa-paper-plane mr-3"></span> Settings</a>
            </li>
        </ul>

    </nav>

    <!-- Page Content  -->
    <div id="content" class="p-4 p-md-5 pt-5">
        <h2 class="mb-4">JSON Settings</h2>
        <hr>
        <p>
            <button id="setJSON">Read JSON from JSON config file</button>
            <button id="getJSON">Display raw JSON</button>
            <button id='submit'>Submit (console.log)</button>
        </p>
        <div id="jsoneditor"></div>

        <script>
            // create the editor
            const container = document.getElementById('jsoneditor')
            const options = {}
            const editor = new JSONEditor(container, options)

            // set json
            document.getElementById('setJSON').onclick = function () {
                const json = ${jsondata}
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
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/main.js"></script>
</body>
</html>
