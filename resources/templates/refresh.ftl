<#-- @ftlvariable name="formdata" type="kotlin.collections.List<com.jetbrains.handson.website.BlogEntry.FormEntry>" -->
<!DOCTYPE html>
<html>
<head>
    <script type='text/javascript' src='/static/js/knockout-3.5.1.js'></script>
    <style id="compiled-css" type="text/css">
        form {
            max-width: 900px;
            display: block;
            margin: 0 auto;
            padding: 12px 20px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<form action="/submit" method="post">
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
    <input type="submit">
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
<hr>
</body>
</html>