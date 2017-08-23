/**
 * Created by ��ΰ on 2017/8/19.
 */
//javascriptģ�黯

var seckill={
    //��װ��ɱ�ȹص�ajax��URL
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

    //��֤�ֻ���
    validatePhone:function (phone) {
        if(phone&&phone.length==11&&!isNaN(phone))
            return true;
        else
            return false;
    },
    
    //������ɱ�߼�//��ȡ��ɱ��ַ������ʵ���߼���ִ����ɱ�������û���
    handleSeckillKill:function (seckillId,node) {
        node.hide().html('<button class = "btn btn-primary btn-lg" id=killBtn>��ʼ��ɱ</button>');

        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            //�ڻص�������ִ�н�������
            if(result&&result['success']){
                var exposer =  result['data'];//��ȡ������
                if(exposer['exposed']){//������ɱ
                    //��ȡ��ɱ��ַ������button
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log('killUrl='+killUrl);//todo
                    //clickһֱ���Ե� one����ֻ��һ������¼�
                    $('#killBtn').one('click',function () {
                        //ִ����ɱ�������
                        $('this').addClass('disabled');//���ð�ť
                        $.post(killUrl,{},function (result) {//������ɱ����
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                            else{
                                console.log("��ɱ����");//
                                console.log("ԭ��"+result['error']);//todo
                            }
                        });
                    });
                    node.show();
                }else{//δ������ɱ,����ʱ�䳤��ʱ�����ƫ��
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

    //��ʱ����
    countdown:function(seckillId,nowTime,startTime,endTime){
        var seckillBox = $('#seckill-box');
        //�¼��ж�
        //��ɱ����
        if(nowTime>endTime){
            seckillBox.html('��ɱ��������');
        }else if(nowTime<startTime){
            //��ɱδ��ʼ,��ʱ�¼��İ�
            var killTime = new Date(startTime+1000);
            seckillBox.countdown(killTime,function (event) {
                var format = event.strftime('��ɱ����ʱ��%D�� %HСʱ %M���� %S��');
                seckillBox.html(format);
                //��ɱ��ʼʱ�Ļص��¼�
            }).on('finish.countdown',function(){
                //��ȡ��ɱ��ַ������ʵ���߼���ִ����ɱ�������û���
                seckill.handleSeckillKill(seckillId,seckillBox);
            })
        }else{//��ɱ��ʼ
            seckill.handleSeckillKill(seckillId,seckillBox);
        }
    },

    //������ɱҳ
    detail:{
        //����ҳ��ʼ��(������ʾ��¼)
        init :function(params){
            //�ֻ���֤�͵�¼����ʱ����
            //�滮��������
            //��cookie�в����ֻ���
            var killPhone = $.cookie('killPhone');

            //��֤�ֻ���
            if(!seckill.validatePhone(killPhone)){
                //���ֻ��� killPhoneModal
                var killPhoneModal =$('#killPhoneModal');
                //��ʾ������
                killPhoneModal.modal({
                    show:true,//��ʾ������
                    backdrop:'static',//��ֹλ�ùر�
                    keyboard:false//�رռ����¼�
                });

                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)){
                        //�绰д��cookie(�������)
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //ˢ��ҳ��
                        window.location.reload();
                    }else{
                        //todo �����İ���Ϣ��ȡ��ǰ���ֵ���
                        $('#killPhoneMessage').hide().html(
                            '<label class="label label-danger">�ֻ��Ŵ���!</label>').show(300);
                    }
                });
            }

            //�Ѿ���¼
            //��ʱ��ؽ���
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function(result){
                if(result&&result['success']){
                    var nowTime=result['data'];
                    //ʱ���жϣ���������߼�����
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }
                else{
                    console.log("result:"+result);
                }
            });
        }
    }
}