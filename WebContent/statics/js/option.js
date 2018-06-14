define(function (require, exports, module) {
    return {
        rem : function(){
            //设置字体大小
		    (function(g){var f=g.document,d=f.documentElement;var b=375,e=b/100,c="orientationchange" in g?"orientationchange":"resize",a=function(){$('body').hide();setTimeout(function(){$('body').show()},10);var h=d.clientWidth||320;if(h>750){h=750}d.style.fontSize=h/e+"px"};if(!f.addEventListener){return}g.addEventListener(c,a,true);setTimeout(a)})(window);
            
            $('.loadingbg').remove(); //清除loding
            $('body').addClass('animated fadeInDown'); //渐进效果
        },
        selectOption : function(){
            for(var i=0;i<$('.select').length;i++){
                $('.select').eq(i).find('strong').text($('.select').eq(i).find('select option:selected').html());
            }

            $('.select>select').on('change',function(){
                $(this).parents('.select').find('strong').text($(this).parents('.select').find('select option:selected').html());
            })  
        }
    }
})