<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="formdata" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/style.css">
    <script type='text/javascript' src='/static/js/knockout-3.5.1.js'></script>
</head>
<body>

<!-- Navigation bar START -->
<div class="header_image clearfix" style="padding: 2px 15px;color: #f2f2f2;background-color: #292929">
    <a href="/"><img src="/static/redhatlogo.svg" width="180" height="35"/></a>
    <h2 style = "vertical-align:middle;">CEEnter</h2>
    <p style="float: right">version ${version}</p>
</div>

<div class="sidebar">
    <a class="active" href="/order"> Order </a>
    <a href="/jsonConfig"> JSON Config </a>
    <a href="/setting"> Settings </a>
</div>
<!-- Navigation bar END -->

<!-- Content START -->
<div class="content">
    <h1>Order</h1>
    <hr>
    <form action="/submit" method="post">
        <ul class="form-style-2">
            <#list formdata as item>
                <#if item?index == 0>
                    <div class="form-group">
                        <label for="${item.options}">${item.label}</label>
                        <select class="form-control" id="${item.options}"
                                data-bind="options: ${item.options}, value: ${item.value}"></select>
                    </div>
                <#else>
                    <#if item.type == "list">
                        <div class="form-group" data-bind="${item.ifdatabind?no_esc}">
                            <label for="${item.options}">${item.label}</label>
                            <select class="form-control" id="${item.options}"
                                    data-bind="options: ${item.options}, value: ${item.value}"></select>
                        </div>
                    <#else>
                        <div class="form-group" data-bind="${item.ifdatabind?no_esc}">
                            <label for="${item.options}">${item.label}</label>
                            <input data-bind="value: ${item.value}" /><br>
                        </div>
                    </#if>
                </#if>
            </#list>
            <br>
            <input type="submit" value=" Order ">
        </ul>
    </form>

    <script type="text/javascript">
        var viewModel = {
            <#list formdata as item>
            <#if item.type == "list">
            ${item.options}: ${item.databind?no_esc},
            </#if>
            <#if item?has_next>
            <#if item.type == "list">
            ${item.value}: ko.observable(),
            <#else>
            ${item.value}: ko.observable("${item.databind?no_esc}"),
            </#if>
            <#else>
            <#if item.type == "list">
            ${item.value}: ko.observable()
            <#else>
            ${item.value}: ko.observable("${item.databind?no_esc}")
            </#if>
            </#if>
            </#list>
        };
        ko.applyBindings(viewModel, document.body);
    </script>
</div>
<!-- Content END -->

</body>
</html>
