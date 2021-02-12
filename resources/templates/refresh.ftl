<#-- @ftlvariable name="formdata" type="kotlin.collections.List<com.jetbrains.handson.website.BlogEntry.FormEntry>" -->
<!DOCTYPE html>
<html>
<head>
    <script type='text/javascript' src='/static/js/vue.min.js'></script>
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
    <label>Request Type
        <select v-model="request_type" @change="clearAll">
            <option disabled value="">Please select one</option>
            <option v-for="option in request_type_options" v-bind:value="option.value">
                {{ option.text }}
            </option>
        </select>
    </label>
    <div v-if="request_type === 'Service Type'">
        <label>Service Type
            <select v-model="service_type">
                <option disabled value="">Please select one</option>
                <option v-for="option in service_type_options" v-bind:value="option.value">
                    {{ option.text }}
                </option>
            </select>
        </label>
    </div>
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
    const app = new Vue({
        el:'#app',
        data: {
            request_type: '',
            request_type_default: '',
            request_type_options: [
                { text: 'Service', value: 'Service Type' },
                { text: 'Task', value: '' },
                { text: 'Report', value: '' }
            ],
            service_type: '',
            service_type_default: '',
            service_type_options: [
                { text: 'VM', value: 'Platform' },
                { text: 'Storage', value: 'Storage' },
                { text: 'Network', value: 'Network' },
                { text: 'Application', value: 'Application' }
            ],
            platform: '',
            platform_default: 'VM Operation',
            platform_options: [
                { text: 'RHV', value: 'RHV' },
                { text: 'GCP', value: 'GCP' },
                { text: 'Azure', value: 'Azure' },
                { text: 'OpenStack', value: 'OpenStack' }
            ],
            vm_operation: '',
            vm_operation_default: '',
            vm_operation_options: [
                { text: 'Create', value: 'Create' },
                { text: 'List', value: 'List' },
                { text: 'Start', value: 'Start' },
                { text: 'Stop', value: 'Stop' }
            ]
        },
        methods: {
            submit () {
                const data = {
                    request_type: this.request_type,
                    service_type: this.service_type,
                    platform: this.platform,
                    vm_operation: this.vm_operation
                }
                alert(JSON.stringify(data, null, 2))
            },
            clearAll:function() {
                this.service_type = '';
                this.platform = '';
                this.vm_operation = '';
            }
        }
    });
</script>
<hr>
</body>
</html>