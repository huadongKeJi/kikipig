define(function (require, exports, module) {
    require(['common','option','slider','swiper'], function(c,option,s) {
        option.rem(); // 设置字体

        $(function() {
            // 调用轮播
            setTimeout(function(){
                s.sliderInit();
            },100)

            $('.jqHomeTap>div').click(function(){
                $(this).addClass('active').siblings().removeClass('active');
                $('.hometap').eq($(this).index()).show().siblings('.hometap').hide();
            })
        })
    })
})