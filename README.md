# IE400
IE400 project 

1- distance is calculated using Manhattan distance formula:

Math.​abs​((x1 - x2)) + Math.​abs(​(y1 - y2));

2- intersectsWithRectangle() method basically checks if the target position intersect with a rectangle or not.

-> x_val is rectangle’ s x value

-> y_val is rectangle’s y value

-> x1 is current x value -> y1 is current y value

if ((x_val <= x1 && x1 <= x_val + width) && (y_val <= y1 && y1 <= y_val + height))

result = true;

else

result = false;
