function togglesidebar() {
	if ($(".sidebar").is(":visible")) {
		$(".sidebar").hide();
		$(".content").css("margin-left", "2%");
	} else {
		$(".sidebar").show();
		$(".content").css("margin-left", "18%");
	}
};