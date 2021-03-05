<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="formdata" type="com.ceeredhat.FormVue" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/style.css">
    <script type="text/javascript" src="/static/js/vue.min.js"></script>
    <script type="text/javascript" src="/static/js/vfg-core.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/vfg-core.css">
    <link rel="stylesheet" type="text/css" href="/static/css/vue-form-generator.css">
</head>
<body>

<!-- Page Header -->
<div class="header_image clearfix" style="padding: 2px 15px;color: #f2f2f2;background-color: #292929">
    <a href="/"><img src="/static/redhatlogo.svg" width="180" height="35" alt=""/></a>
    <h2 style="vertical-align:middle;">CEEnter</h2>
    <p style="float: right">version ${version}</p>
</div>
<!-- Navigation bar START -->
<div class="sidebar">
    <a class="active" href="/ordertower"> Order/Tower </a>
    <a href="/order"> Order/JSON </a>
    <a href="/jsonConfig"> JSON Config </a>
    <a href="/setting"> Settings </a>
</div>
<!-- Navigation bar END -->

<!-- Content START -->
<div class="content">
    <h1>Order based on Tower templates</h1>
    <hr>
    <div class="row">
        <div class="container" id="app">
            <div class="column">
                <div class="panel panel-default">
                    <div class="panel-heading">Form</div>
                    <div class="panel-body">
                        <vue-form-generator :schema="schema" :model="model" :options="formOptions"></vue-form-generator>
                        <button @click="APIcommandClick" type="button" class="panel-body"> Generate API Command</button>
                        <button @click="APIexecuteClick" type="button" class="panel-body"> Execute</button>
                        <button @click="clearClick(model)" type="button" class="panel-body"> Clear</button>
                    </div>
                </div>
            </div>
            <div class="column">
                <div class="panel">
                    <h2 class="panel-body">API Command</h2>
                    <p v-html="APIcommand" class="panel-body"></p>
                </div>
                <div class="panel">
                    <h2 class="panel-body">API Output</h2>
                    <p v-html="APIoutput" class="panel-body"></p>
                </div>
            </div>
        </div>
    </div>
    <!-- JS code -->
    <script type="text/javascript">
        const VueFormGenerator = window.VueFormGenerator;
        const vm = new Vue({
            el: "#app",
            components: {
                "vue-form-generator": VueFormGenerator.component
            },
            methods: {
                APIcommandClick: function () {
                    this.APIcommand = "curl -f -k -H 'Content-Type: application/json' -H 'Authorization:Basic ";
                },
                APIexecuteClick: function () {
                    this.APIoutput = "############ Workflow Name: Test-Workflow, Status: successful ##################";
                },
                clearClick: function (model) {
                    <#list formdata as item>
                    ${item.clearField?no_esc}
                    </#list>
                }
            },
            data: {
                model: {
                    <#list formdata as item>
                    <#if item?has_next>
                    ${item.modelName?no_esc},
                    <#else>
                    ${item.modelName?no_esc}
                    </#if>
                    </#list>
                },
                schema: {
                    fields: [
                        <#list formdata as item>
                        <#if item?has_next>
                        {
                            ${item.schemaType?no_esc},
                            ${item.schemaModel?no_esc},
                            ${item.schemaReadonly?no_esc},
                            ${item.schemaFeatured?no_esc},
                            ${item.schemaRequired?no_esc},
                            ${item.schemaDisabled?no_esc},
                            <#if item.schemaDefault?has_content>
                            ${item.schemaDefault?no_esc},
                            </#if>
                            <#if item.schemaPlaceholder?has_content>
                            ${item.schemaPlaceholder?no_esc},
                            </#if>
                            <#if item.schemaValues?has_content>
                            ${item.schemaValues?no_esc},
                            </#if>
                            <#if item.schemaValidator?has_content>
                            ${item.schemaValidator?no_esc},
                            </#if>
                            <#if item.schemaVisible?has_content>
                            ${item.schemaVisible?no_esc},
                            </#if>
                            ${item.schemaLabel?no_esc}
                        },
                        <#else>
                        {
                            ${item.schemaType?no_esc},
                            ${item.schemaModel?no_esc},
                            ${item.schemaReadonly?no_esc},
                            ${item.schemaFeatured?no_esc},
                            ${item.schemaRequired?no_esc},
                            ${item.schemaDisabled?no_esc},
                            <#if item.schemaDefault?has_content>
                            ${item.schemaDefault?no_esc},
                            </#if>
                            <#if item.schemaPlaceholder?has_content>
                            ${item.schemaPlaceholder?no_esc},
                            </#if>
                            <#if item.schemaValues?has_content>
                            ${item.schemaValues?no_esc},
                            </#if>
                            <#if item.schemaValidator?has_content>
                            ${item.schemaValidator?no_esc},
                            </#if>
                            <#if item.schemaVisible?has_content>
                            ${item.schemaVisible?no_esc},
                            </#if>
                            ${item.schemaLabel?no_esc}
                        }
                        </#if>
                        </#list>
                    ]
                },
                formOptions: {
                    validateAfterLoad: true,
                    validateAfterChanged: true
                },
                APIcommand: "...",
                APIoutput: "..."
            }
        });
    </script>
</div>
<!-- Content END -->
</body>
</html>
