/**
 * Created by 宽伟 on 2017/8/19.
 */
//javascript模块化

var seckill={
    //封装秒杀先关的ajax的URL
    URL :{
        now :function () {
            return '/seckill/time/now';
        },
        exposer:function (seckillId){
            return '/seckill/'+seckillId+'/exposer';
        },
        execution:function(seckillId,md5){
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }

    },

    //验证手机号
    validatePhone:function (phone) {
        if(phone&&phone.length==11&&!isNaN(phone))
            return true;
        else
            return false;
    },
    
    //处理秒杀逻辑//获取秒杀地址，控制实现逻辑，执行秒杀操作（用户）
    handleSeckillKill:function (seckillId,node) {
        node.hide().html('<button class = "btn btn-primary btn-lg" id=killBtn>开始秒杀</button>');

        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            //在回调函数中执行交互流程
            if(result&&result['success']){
                var exposer =  result['data'];//获取的数据
                if(exposer['exposed']){//开启秒杀
                    //获取秒杀地址，传入button
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log('killUrl='+killUrl);//todo
                    //click一直可以点 one可以只绑定一个点击事件
                    $('#killBtn').one('click',function () {
                        //执行秒杀请求操作
                        $('this').addClass('disabled');//禁用按钮
                        $.post(killUrl,{},function (result) {//发送秒杀请求
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                            else{
                                console.log("秒杀出错");//
                                console.log("原因："+result['error']);//todo
                            }
                        });
                    });
                    node.show();
                }else{//未开启秒杀,运行时间长，时间出现偏差
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log('result='+result);
            }

        });

    },

    //计时函数
    countdown:function(seckillId,nowTime,startTime,endTime){
        var seckillBox = $('#seckill-box');
        //事件判断
        //秒杀结束
        if(nowTime>endTime){
            seckillBox.html('秒杀结束！！');
        }else if(nowTime<startTime){
            //秒杀未开始,计时事件的绑定
            var killTime = new Date(startTime+1000);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H小时 %M分钟 %S秒');
                seckillBox.html(format);
                //秒杀开始时的回调事件
            }).on('finish.countdown',function(){
                //获取秒杀地址，控制实现逻辑，执行秒杀操作（用户）
                seckill.handleSeckillKill(seckillId,seckillBox);
            })
        }else{//秒杀开始
            seckill.handleSeckillKill(seckillId,seckillBox);
        }
    },

    //详情秒杀页
    detail:{
        //详情页初始化(包括提示登录)
        init :function(params){
            //手机验证和登录，计时交互
            //规划交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');

            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                //绑定手机号 killPhoneModal
                var killPhoneModal =$('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show:true,//显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keyboard:false//关闭键盘事件
                });

                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //电话写入cookie(七天过期)
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        //todo 错误文案信息抽取到前端字典里
                        $('#killPhoneMessage').hide().html(
                            '<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }

            //已经登录
            //计时相关交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function(result){
                if(result&&result['success']){
                    var nowTime=result['data'];
                    //时间判断，方便进行逻辑处理
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }
                else{
                    console.log("result:"+result);
                }
            });
        }
    }
}