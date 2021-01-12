<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="data" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/spectre.min.css">
    <link rel="stylesheet" href="/static/css/spectre-exp.min.css">
    <link rel="stylesheet" href="/static/css/spectre-icons.min.css">
    <script src="/static/js/jsoneditor.min.js"></script>
    <script>
        // Set the default CSS theme and icon library globally
        JSONEditor.defaults.theme = 'spectre';
        JSONEditor.defaults.iconlib = 'spectre';
    </script>
    <style>
        .container {
            max-width:960px;
            margin: 0 auto
        }
    </style>
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
        <div id='editor_holder'></div>
        <button id='submit'>Submit (console.log)</button>
        <script>
            // Initialize the editor with a JSON schema
            var editor = new JSONEditor(document.getElementById('editor_holder'),{
                schema: {
                    type: "object",
                    title: "Car",
                    properties: {
                        make: {
                            type: "string",
                            enum: [
                                "Toyota",
                                "BMW",
                                "Honda",
                                "Ford",
                                "Chevy",
                                "VW"
                            ]
                        },
                        model: {
                            type: "string"
                        },
                        year: {
                            type: "integer",
                            enum: [
                                1995,1996,1997,1998,1999,
                                2000,2001,2002,2003,2004,
                                2005,2006,2007,2008,2009,
                                2010,2011,2012,2013,2014
                            ],
                            default: 2008
                        },
                        safety: {
                            type: "integer",
                            format: "rating",
                            maximum: "5",
                            exclusiveMaximum: false,
                            readonly: false
                        }
                    }
                }
            });

            // Hook up the submit button to log to the console
            document.getElementById('submit').addEventListener('click',function() {
                // Get the value from the editor
                console.log(editor.getValue());
            });
        </script>
    </div>
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/main.js"></script>
</body>
</html>
