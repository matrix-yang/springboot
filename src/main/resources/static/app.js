new Vue({
    el: '#app',
    data: function () {
        return {
            tableData: [],
            dialogVisible: false,
            labelPosition: 'right',
            formLabelAlign: {
                name: '',
                sex: '',
                teacher: ''
            }
        }
    },

    methods:{
        open:function () {
          this.dialogVisible=true;
        },
        createStu: function () {
            this.dialogVisible = false;
            var self=this;
            $.ajax({
                type: "get",
                url: 'http://localhost:8080/student/add',
                dataType: "json",
                data: self.formLabelAlign,
                success: function (data) {
                    var temp='';
                    if (self.formLabelAlign.sex==1){
                        temp='男';
                    }else {
                        temp='女';
                    }
                    self.tableData.push({
                        name: self.formLabelAlign.name,
                        sex: temp,
                        teacher: self.formLabelAlign.teacher
                    });
                    alert("添加成功");
                },
                error: function (err) {
                    alert("添加失败");
                }
            });
        },
        findAll:function () {
            var self=this;
            $.ajax({
                type: "get",
                url: 'http://localhost:8080/student/getAll',
                dataType: "json",
                success: function (data) {
                    for(var i=0;i<data.length;i++){
                        if (data[i].sex==1){
                            data[i].sex='男'
                        }else {
                            data[i].sex='女'
                        }
                        self.tableData.push({
                            name:data[i].name,
                            sex:data[i].sex,
                            teacher:data[i].teacher
                        });
                    }
                },
                error: function (err) {
                    alert("添加失败");
                }
            });
        },

    },
    mounted: function () {
        this.findAll();
    },
})
;