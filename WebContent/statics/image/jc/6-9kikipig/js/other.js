define(function (require, exports, module) {
    require(['common','option','slider','swiper'], function(c,option,s) {
        option.rem(); // 设置字体
        option.selectOption();

        $(function() {
            // 调用轮播
            setTimeout(function(){
                s.sliderInit();
            },100)

            $('.jqXcTop>div').click(function(){
                $(this).addClass('active').siblings().removeClass('active');
            })

            $('.jqpx').click(function(){
                $(this).find('.pxSelect').toggleClass('down');
            })
            
        })
    })
})