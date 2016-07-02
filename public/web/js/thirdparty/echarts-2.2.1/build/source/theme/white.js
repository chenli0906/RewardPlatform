define(function() {

var theme = {
    // 默认色板
    color: [
        '#c12e34','#e6b600','#0098d9','#2b821d',
        '#005eaa','#339ca8','#cda819','#32a487'
    ],

    // 图表标题
    title: {
//       x: '0',//已经在homePageLineChart.js里设置
//       y: '0',
        textStyle: {
//            fontWeight: 'normal',
            fontSize: 16,
            fontWeight: 'lighter',
            color: '#ffffff'
        },
         subtext: ''//y轴标题
    },

    
    // 值域
    dataRange: {
        color:['#1790cf','#a2d4e6']
    },

    // 工具箱
    toolbox: {
        color : ['#06467c','#00613c','#872d2f','#c47630']
    },

    // 悬浮框框
    tooltip: {
        backgroundColor: 'rgba(256,256,256,0)',// 悬浮框的颜色
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'line',         // 默认为直线，可选为：'line' | 'shadow'
            lineStyle : {          // 直线指示器样式设置
                color: 'rgba(256,256,256,0)',// 纵向竖虚线的颜色
                type: 'dashed'
            },
            crossStyle: {
                color: 'rgba(250,250,250,0)'
            },
            shadowStyle : {                     // 阴影指示器样式设置
                color: 'rgba(154,217,247,0.2)'
            }
        },
    textStyle:{
     color: 'rgba(250,250,250,0)'// 悬浮框内容的颜色
    }

    },

    // 区域缩放控制器
    dataZoom: {
        dataBackgroundColor: 'rgba(250,250,250,0)',            // 数据背景颜色
        fillerColor: 'rgba(250,250,250,0)',   // 填充颜色
        handleColor: '#FFFFFF'     // 手柄颜色
    },
    
    // 网格
    grid: {
        borderWidth: 0
    },

    // 类目轴
    categoryAxis: {
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color:  'rgba(66, 115, 173,1)'//横轴最下面一根线条的颜色
            }
        },
        splitLine: {           // 分隔线
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: ['#FFFFFF']
            }
        }
    },

    // 数值型坐标轴默认参数
    valueAxis: {
        axisLine: {            // 坐标轴线
            lineStyle: {       // 属性lineStyle控制线条样式
                color: 'rgba(66, 115, 173,1)'//纵轴线条的颜色RGB(0, 142, 211)
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0.1)','rgba(250,250,250,0.1)']
            }
        },
        splitLine: {           // 分隔线
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: 'rgba(66, 115, 173,1)'//横轴所有线条的颜色
            }
        }
    },

    timeline : {
        lineStyle : {
            color : '#FFFFFF'
        },
        controlStyle : {
            normal : { color : '#FFFFFF'},
            emphasis : { color : '#FFFFFF'}
        }
    },

    // K线图默认参数
    k: {
        itemStyle: {
            normal: {
                color: '#FFFFFF',          // 阳线填充颜色
                color0: '#FFFFFF',      // 阴线填充颜色
                lineStyle: {
                    width: 1,
                    color: '#FFFFFF',   // 阳线边框颜色
                    color0: '#FFFFFF'   // 阴线边框颜色
                }
            }
        }
    },
    
    map: {
        itemStyle: {
            normal: {
                areaStyle: {
                    color: '#FFFFFF'
                },
                label: {
                    textStyle: {
                        color: '#FFFFFF'
                    }
                }
            },
            emphasis: {                 // 也是选中样式
                areaStyle: {
                    color: '#FFFFFF'
                },
                label: {
                    textStyle: {
                        color: '#FFFFFF'
                    }
                }
            }
        }
    },
    
    force : {
        itemStyle: {
            normal: {
                linkStyle : {
                    color : '#FFFFFF'
                }
            }
        }
    },
    
    chord : {
        padding : 4,
        itemStyle : {
            normal : {
                borderWidth: 1,
                borderColor: 'rgba(250, 250, 250, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(250, 250, 250, 0.5)'
                    }
                }
            },
            emphasis : {
                borderWidth: 1,
                borderColor: 'rgba(250, 250, 250, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(250, 250, 250, 0.5)'
                    }
                }
            }
        }
    },
    
    gauge : {
        axisLine: {            // 坐标轴线
            show: true,        // 默认显示，属性show控制显示与否
            lineStyle: {       // 属性lineStyle控制线条样式
                color: [[0.2, '#FFFFFF'],[0.8, '#FFFFFF'],[1, '#FFFFFF']],
                width: 8
            }
        },
        axisTick: {            // 坐标轴小标记
            splitNumber: 10,   // 每份split细分多少段
            length :12,        // 属性length控制线长
            lineStyle: {       // 属性lineStyle控制线条样式
                color: '#FFFFFF'
            }
        },
        axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                color: '#FFFFFF'
            }
        },
        splitLine: {           // 分隔线
            length : 18,         // 属性length控制线长
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                color: '#FFFFFF'
            }
        },
        pointer : {
            length : '90%',
            color : '#FFFFFF'
        },
        title : {
            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                color: '#FFFFFF'
            }
        },
        detail : {
            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                color: '#FFFFFF'
            }
        }
    },
    
    textStyle: {
        fontFamily: '微软雅黑, Arial, Verdana, sans-serif'
    }
};

    return theme;
});