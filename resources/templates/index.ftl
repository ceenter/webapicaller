<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="data" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
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
        <h1 class="mb-4">Catalog Caller</h1>
        The API Caller is an example GUI which helps generating the proper API syntax. <br>
            Ultimately it may also execute the API call, but not at this stage yet.<br>
            The metadata for API Caller is under the api-caller folder.<br>
            The first file is the menu-map.json where the interactive menu options are defined.<br>
    </div>
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/main.js"></script>
</body>
</html>
