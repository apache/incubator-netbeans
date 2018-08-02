<?php
if ($i == 0) {
    if ($j == 0)
    {
    } // if2
} elseif ($i == 1) { // if
    if ($j == 2) {
        $l = 33;
    } else {
        $l = 22;
    }
} else { // elseif

} // else

if (true) {
    echo "Use ${complexSyntax}";
} else {
    echo "Test";
} // complex syntax

if ($i == 0) :
    $j = 11;
elseif ($i == 1) : // alternative if
    if ($j == 1) :
        $k = 1;
        echo "Use ${complexSyntax}";
    else : // alternative nested if
        $k = 2;
    endif; // alternative nested else
else : // alternative elseif
    $j = 33;
endif; // alternative else

while (true)
{

} // while

while (true) :

endwhile; // alternative while

do
{
    
} while (true); // do

for ($index = 0; $index < count($array); $index++)
{
    
} // for

for ($index = 0; $index < count($array); $index++) :
    
endfor; // alternative for


foreach ($array as $value)
{
    
} // foreach

foreach ($array as $value) :
    
endforeach; // alternative foreach

switch ($value)
{
    case $value:

        break;

    default:
        break;
} // switch

switch ($value) :
    case $value:

        break;

    default:
        break;
endswitch; // alternative switch

class Foo {

    public function foo(){
        
    } // foo method

} // Foo class

class Bar
{
    private function bar()
    {
        
    } // bar method

} // Bar class

interface FooInterface
{
    
} // FooInterface

abstract class AbstractClass
{
    
} // AbstractClass

trait FooTrait
{
    
} // FooTrait

try
{
    
} // try
catch (Exception $ex)
{
    
} // catch
finally
{
    
} // finally
