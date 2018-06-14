require.config({
    // 配置路径信息
    baseUrl: './js',

    // 文件路径（修改别名）
    paths : {
        "common"        :   ["./common"],
        "jquery"        :   ["./jquery"],
        "swiper"        :   ["./swiper"],
        "slider"        :   ["./slider"],
        "option"        :   ["./option"]
        
    },

    // 依赖加载
    shim: {
        common          :   ['jquery'],
        commonoption    :   ['jquery'],
        option          :   ['jquery'],
        swiper          :   ['common'],
        slider          :   ['common'],
        slider          :   ['swiper']
    }
})
