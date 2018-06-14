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

            if($('.ki_banner').length > 0){
                require(['slider','swiper'], function(s) {
                    option.rem(); // 设置字体
            
                    // 调用轮播
                    setTimeout(function(){
                        s.sliderInit();
                    },100)
                })
            }

            $('.jqMezu').click(function(){
                $('.prize_shadow').addClass('active');
                $('.shadow').show();
            })
            $('.shadow').click(function(){
                if($('.prize_shadow').hasClass('active')){
                    $('.prize_shadow').removeClass('active');
                    $('.shadow').hide();
                }
            })

            // 视频
            if($('video').length > 0){
                var myVideo = document.getElementById("video");
                myVideo.oncanplay = function(){
                    //myVideo.currentTime = 1;
                }

                $('.startVideo').click(function(){
                    myVideo.play();
                    $(this).hide();
                })

                // 视频结束时
                myVideo.onended = function() {
                    $('.startVideo').show();
                    $(myVideo).prop("poster",$(myVideo).prop("poster"));
                };

                // 视频暂停时
                myVideo.onpause=function(){
                    $('.startVideo').show();
                };
                
                // 视频播放时
                myVideo.onplay=function(){
                    $('.startVideo').hide();
                };
            }
            
        })
    })
})