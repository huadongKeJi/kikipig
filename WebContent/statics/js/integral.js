define(function (require, exports, module) {
    require(['common'], function(s,r,c) {
        $(function() {
            $('.tab a').click(function(){
                var _index = $(this).index();
                $(this).addClass('active').siblings('a').removeClass('active');
                $('.tab-content>section').eq(_index).show().siblings('section').hide();
            })
        })
    })
})