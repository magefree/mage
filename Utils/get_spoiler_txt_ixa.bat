del x
del x2
del x3
del x4
del x5
del x6
del x7.bat
echo "" |  cut.pl stdin "http://mythicspoiler.com/ixa/" 0 wget > x
type x | cut.pl stdin "cards\/" "\n\n\ncards/" replace  > x2
type x2 | cut.pl stdin "^" "\nhttp://mythicspoiler.com/ixa/" replace  > x3
find x3 "http://mythicspoiler.com/ixa/" | find /v /i "jpg"  > x4
type x4 | cut.pl stdin "><.*" "" replace  > x5
type x5 | cut.pl stdin "^" "echo '''' | cut.pl stdin ''" replace  > x6
echo @echo off > x7.bat
type x6 | cut.pl stdin "$" " 0 wget_card_spoiler" replace | cut.pl stdin 0 0 uniquelines   | find  /I "cards" >> x7.bat
type x7.bat
