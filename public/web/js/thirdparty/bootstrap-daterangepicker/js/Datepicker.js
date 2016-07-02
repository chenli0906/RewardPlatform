/*
 *@ToDo: init RangeDatePicker in HistoryJobList
 *
 */
 $(function () {
	var cb = function(start, end, label) {
		$('#reportrange span').html(start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
		//var newUrl = "quartz/"+currentConfigName+"/jobhistory";
		//newUrl = newUrl+"?startTime="+start.format('YYYY-MM-DD')+"&endTime="+end.format('YYYY-MM-DD')+"";
		//$('#historylist').DataTable().ajax.url(newUrl).load();
	};
	var optionSet = {
//	startDate: moment(),
//	endDate: moment(),
	opens: 'center',
//	ranges: {
//		'Today': [moment(), moment()],		   
//		'Last 7 Days': [moment().subtract(6, 'days'), moment()],
//		'Last 30 Days': [moment().subtract(29, 'days'), moment()]		   
//	},
	format: 'YYYY/MM/DD',
	separator: ' to ',
	locale: {
		applyLabel: 'Apply',
		cancelLabel: 'Clear',
		customRangeLabel: 'Calendar'
		}
	};

	  $('#reportrange span').html(moment().format('YYYY-MM-DD') + '  to  ' + moment().format('YYYY-MM-DD'));

	  $('#reportrange').daterangepicker(optionSet, cb);

	  $('#reportrange').on('show.daterangepicker', function() { 
		});
	  $('#reportrange').on('hide.daterangepicker', function() { 
		sDate = $('#reportrange span').html().split('to')[0];
		eDate = $('#reportrange span').html().split('to')[1];
	  });
});