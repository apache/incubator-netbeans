<?php

echo preg_replace_callback('~-([a-z])~', function ($match) {
    return strtoupper($match[1]);
}, 'hello-world');


$greet = function($name)
{
    printf("Hello %s\r\n", $name);
};
?>