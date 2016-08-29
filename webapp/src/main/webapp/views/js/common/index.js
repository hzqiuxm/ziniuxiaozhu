/**
 * Created by hzqiuxm on 2016/8/12 0012.
 */
$(function () {
    $('.sociable li a img').tipsy({gravity: 'n'});
});
$(window).on('load',function () {
    $('.flexslider').flexslider();
});

jQuery(function ($) {
    $("#tweet").tweet({
        avatar_size: 32,
        count: 2,
        query: "themeforest",
        loading_text: "Loading Tweets..."
    });
});


// initialise plugins
jQuery(function () {
    // Banners button
    jQuery('ul.banners_cycle li').mouseenter(function () {
        jQuery(this).find('.button').stop(false, true).fadeIn();
    })
    jQuery('ul.banners_cycle li').mouseleave(function () {
        jQuery(this).find('.button').stop(false, true).fadeOut();
    });

    // Banners
    jQuery('ul.banners_cycle li:not(.style-1)').mouseenter(function () {
        jQuery(this).css('z-index', '6');
        jQuery(this).stop(false, true).animate({right: '-64px'}, {easing: "swing", duration: 300});
        jQuery(this).animate({right: '0'});
    });
    jQuery('ul.banners_cycle li:not(.style-1)').mouseleave(function () {
        jQuery(this).stop(false, true).animate({right: '-64px'});
        jQuery(this).stop(false, true).animate({right: '0'}, 'slow');
    });

    // Banner #2
    jQuery('ul.banners_cycle li.style-2').mouseleave(function () {
        jQuery(this).css('z-index', '5');
    });
    // Banner #3
    jQuery('ul.banners_cycle li.style-3').mouseleave(function () {
        jQuery(this).css('z-index', '4');
    });
    // Banner #4
    jQuery('ul.banners_cycle li.style-4').mouseleave(function () {
        jQuery(this).css('z-index', '3');
    });
    // Banner #5
    jQuery('ul.banners_cycle li.style-5').mouseleave(function () {
        jQuery(this).css('z-index', '2');
    });
    // Banner #6
    jQuery('ul.banners_cycle li.style-6').mouseenter(function () {
        jQuery('body').css('overflow-x', 'hidden');
    });
    jQuery('ul.banners_cycle li.style-6').mouseleave(function () {
        jQuery(this).css('z-index', '1');
        jQuery('body').css('overflow-x', 'hidden');
    });
});