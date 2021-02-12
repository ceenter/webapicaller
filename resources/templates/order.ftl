<#-- @ftlvariable name="version" type="com.ceeredhat.IndexData" -->
<#-- @ftlvariable name="formdata" type="com.ceeredhat.IndexData" -->
<html lang="en">
<head>
    <title>CEEnter</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
</head>
<body>

<!-- Navigation bar START -->
<div class="header_image clearfix" style="padding: 2px 15px;color: #f2f2f2;background-color: #292929">
    <a href="/"><img src="/static/redhatlogo.svg" width="180" height="35" alt=""/></a>
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
    <form action="/submit" method="post" class="form-style-6">
    <div id="app">
<#list formdata as item>
    <#if item?index == 0>
        <label>${item.label}
            <select v-model="${item.name}" @change="clearAll">
                <option disabled value="">Please select one</option>
                <option v-for="option in ${item.name}_options" v-bind:value="{value: option.value, text: option.text}">
                    {{ option.text }}
                </option>
            </select>
        </label>
    <#else>
        <#if item.type == "list">
            <div v-if="${item.parent}.value === '${item.label}' || (${item.parent} !== '' && ${item.parent}_default === '${item.label}')">
                <label>${item.label}
                    <select v-model="${item.name}">
                        <option disabled value="">Please select one</option>
                        <option v-for="option in ${item.name}_options" v-bind:value="{value: option.value, text: option.text}">
                            {{ option.text }}
                        </option>
                    </select>
                </label>
            </div>
        </#if>
        <#if item.type == "manual">
            <div v-if="${item.parent}.value === '${item.label}' || (${item.parent} !== '' && ${item.parent}_default === '${item.label}')">
                <label>${item.label}
                    <input v-model="vm_memory" v-bind:placeholder="${item.name}_text">
                </label>
            </div>
        </#if>
        <#if item.type == "auto">
                <div v-if="${item.parent}.value === '${item.label}' || (${item.parent} !== '' && ${item.parent}_default === '${item.label}')">
                <label>${item.label}
                    <input v-model="${item.name}">
                </label>
            </div>
        </#if>
    </#if>
 </#list>
        <hr>
        <p>
            <button @click="submitClick" type="button" class="btn btn-primary"> Show Call </button>
            <button @click="greet" type="button" class="btn btn-primary"> API to console log </button>
            <button @click="clearBtn" type="button" class="btn btn-primary"> Clear All </button>
        </p>
    </div>
    </form>

    <script type="text/javascript">
        const app = new Vue({
            el:'#app',
            data: {
                <#list formdata as item>
                    <#if item.type == "list">
                        <#if item?has_next>
                            ${item.name}: '',
                            ${item.name}_default: '${item.default}',
                            ${item.name}_options: [ ${item.options?no_esc} ],
                        <#else>
                            ${item.name}: '',
                            ${item.name}_default: '${item.default}',
                            ${item.name}_options: [ ${item.options?no_esc} ]
                        </#if>
                    <#else>
                        <#if item?has_next>
                            ${item.name}: '',
                            ${item.name}_text: '${item.default}',
                            ${item.name}_default: '${item.default}',
                        <#else>
                            ${item.name}: '',
                            ${item.name}_text: '${item.default}',
                            ${item.name}_default: '${item.default}'
                        </#if>
                    </#if>
                </#list>
            },
            methods: {
                submitClick: function(){
                    const data = {
                        <#list formdata as item>
                        request_type: this.${item.name}_type.value,
                        </#list>
                    }
                    alert(JSON.stringify(data, null, 2))
                },
                clearAll: function() {
                    <#list formdata as item>
                    <#if item?index != 0>
                    this.${item.name} = '';
                    </#if>
                    </#list>
                },
                clearBtn: function() {
                    <#list formdata as item>
                    this.${item.name} = '';
                    </#list>
                },
                greet: function(){
                    let tmplName =
                        <#list formdata as item>
                        <#if item?has_next>
                        this.${item.name}.text + "-" +
                        <#else>
                        this.${item.name}.text;
                        </#if>
                        </#list>
                    console.log("Ansible Workflow Template: " + tmplName)
                },
                vm_memory_set: function(){
                    this.vm_memory = '2';
                    console.log("vm change..")
                }
            }
        });
    </script>
</div>
<!-- Content END -->

</body>
</html>
