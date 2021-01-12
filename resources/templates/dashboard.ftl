<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="data" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
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
        <h1 class="mb-4">Dashboard</h1>
        <hr>
        <form id="frm_test" class="form-vertical" method="post">
            <table id="mytable">
                <thead>
                <tr>
                    <td>Request Type</td>
                    <td>Service Type</td>
                    <td>Platform</td>
                    <td>VM Operation</td>
                    <td>VM Name</td>
                    <td>VM Flavor</td>
                    <td>Small VM Memory</td>
                    <td>Small VM CPU</td>
                    <td>Small VM Disk Size</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Service</td>
                    <td>VM</td>
                    <td>RHV</td>
                    <td>Create</td>
                    <td>testvm123</td>
                    <td>Small</td>
                    <td>2</td>
                    <td>1</td>
                    <td>20</td>
                </tr>
                </tbody>
            </table>
            <input type="submit"  name="submit" value="submit" />
        </form>
    </div>
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/main.js"></script>
</body>
</html>
