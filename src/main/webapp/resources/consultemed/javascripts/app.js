$(function() {
	$('.js-toggle').bind('click', function(event) {
		$('.js-sidebar, .js-content').toggleClass('is-toggled');
		event.preventDefault();
	});	
});

//Atualizando item selecionado no menu
document.getElementById('menu-' + window.location.href.split('/pages/')[1].split('/')[0])
		.classList.add("is-selected")