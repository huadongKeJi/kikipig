// 轮播
define(function (require, exports, module) {
    return {
        dom: {},
        sliderInit: function() {
            this.initDom();
            this.initSlide();
        },
        initDom: function() {
            var dom = this.dom;
            dom.body = $('body');
            dom.banner = $('.j-banner');
        },
        initSlide: function() {
            var dom = this.dom,
                bannerSlide,
                serviceSlide,
                bannerNums = dom.banner.find('a').length,
                bannerPag = dom.banner.find('.swiper-pagination');

            if (bannerNums > 1) {
                bannerSlide = new Swiper('.banner-block', {
                    initialSlide: bannerNums - 1,
                    speed: 800,
                    autoplay: 5000,
                    loop: true,
                    autoplayDisableOnInteraction:false,
                    pagination: '.swiper-pagination',
                    onInit: function(swiper) {
                        swiper.slideNext(true, 1000);
                        setTimeout(function() {
                            swiper.startAutoplay();
                        }, 1000);
                        bannerPag.css('opacity', 1);
                    },
                });
            }
        }
    }
})