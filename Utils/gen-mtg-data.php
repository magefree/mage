<?php
$filename = 'all.htm';
$file = fopen($filename, 'r');
$data = fread($file, filesize($filename));
fclose($file);
preg_match_all("/<td\r?\n? \r?\n?class=\"number\">(\d*?)<\/td>(.*?)Details.aspx\?multiverseid=(\d+)\"(.*?);\">(.*?)<\/a>(.*?)<td\r?\n? \r?\n?class=\"rarity\">(.{1})<\/td><td\r?\n? \r?\n?class=\"set\">(.*?)<\/td>/s", $data, $matches);
preg_match_all("/Details.aspx?\?multiverseid=(\d+)/", $data, $ids);
$filename = 'mtg-cards-data.txt';
$file = fopen($filename, 'w');
$data = array();
$i = 0;
foreach($matches[3] as $id){
   $data[$i] = array(str_replace("\r\n", '', $matches[5][$i]), str_replace("\r\n", '', $matches[8][$i]), $matches[7][$i], $matches[3][$i], $matches[1][$i]);
   @fwrite($file, "|".$data[$i][0]."|".$data[$i][1]."|".$data[$i][2]."|".$data[$i][3]."|".$data[$i][4]."|\n");
   $i++;
   $found = array_search($id,  $ids[1]);
      if($found === FALSE)
         echo ''.$id."\n";
      else
         unset($ids[1][$found]);

}
fclose($file);

print_r($ids[1]);
?>
