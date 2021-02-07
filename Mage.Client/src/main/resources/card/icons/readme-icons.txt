Requirements for icon files:
* free open source license like creative commons;
* SVG format;
* icon must have square proportional (with 1-2 pixels free space on the border);
* icon must use small view box (example: 16 x 16 px). If you use big image scale then you can't see ingame stroke effect on icon
* icon must be painted with glyph/solid style, filled by black (black-white-none colors possible):
  * white color keeps;
  * black color replaced by themed color;
  * non color is transparent;
* don't use strokes (xmage uses it itself);


Folder structure:
* original - keep original icons by sources, don't edit it;
* prepared - prepared icons, use it in your code (CardIconType), see requirements above;

Icons sources:
  * bootstrap icons: https://github.com/twbs/icons
  * font awesome: https://github.com/FortAwesome/Font-Awesome
  * pixabay: https://pixabay.com/