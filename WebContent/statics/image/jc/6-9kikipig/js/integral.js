define(function (require, exports, module) {
    require(['common','option'], function(c,option) {
        option.rem(); // 设置字体
        option.selectOption();
        $(function() {
            $('.tab a').click(function(){
                var _index = $(this).index();
                $(this).addClass('active').siblings('a').removeClass('active');
                $('.tab-content>section').eq(_index).show().siblings('section').hide();
            })
        })
    })
})