google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawChart);

$("#btnGetChartData").click(function () {
     $("#btnGetChartData").hide();
    $.ajax({
        url: "DataVisualization2",
        type: "POST",
        data: "{}",
        success: function (msg) {
            createDataTable(msg)            
        },
        error: function(){
            console.log("error occurred while making ajax call;")
        }
    });    
});

function createDataTable(jsonData) {
    var parsedData = $.parseJSON(jsonData);
    var data = new Array();
    var productNameArr = new Array();
    var quantityArr = new Array();

    console.log(parsedData);
    var actualData = parsedData.myArrayList;
    data2 = ['Product Name', 'Quantity'];
    productNameArr.push(data2);    
    for(var i=0; i<actualData.length; i++) {
      console.log(actualData[i]);
        var productName = actualData[i].map.product_name;
        var quantity = actualData[i].map.product_quantity;
        var data1 = [];
        data1.push(productName);
        data1.push(quantity);
        productNameArr.push(data1);
     }
     console.log(productNameArr)
     drawChart(data, productNameArr);
}
function drawChart(data, productNameArr) {
  console.log(productNameArr);
  var chartData = google.visualization.arrayToDataTable(productNameArr);

  var materialOptions = {
    'width':700,
    'height':1000,
    chart: {
     /*title: 'Products with Names',
      subtitle: 'Based on the number of times the item was sold'*/
    },
    hAxis: {
      title: 'Quantity',
      minValue: 0,
    },
    vAxis: {
      title: 'Product Name'
    },
    bars: 'horizontal',
    axes: {
      y: {
        0: {side: 'right'}
      }
    }
    };
  var materialChart = new google.charts.Bar(document.getElementById('chart_div'));
  materialChart.draw(chartData, materialOptions);
}
