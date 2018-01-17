#include<Servo.h>

int moveX;
int moveY;

Servo up1;
Servo up2;
Servo turn;

void setup(){
  Serial.begin(9600);
  turn.attach(12);
  up1.attach(10);
  up2.attach(8);
}

void loop(){
//  if(Serial.available()){
//    moveX = Serial.read()-48;
//    moveY = Serial.read()-48;
//    Serial.println(moveX);
//  }
  if(Serial.available()){
    //Shift by 53 so 5=0
    moveX = Serial.read()-53;
    delay(10);
    moveY = Serial.read()-53;
    delay(10);
    if(Serial.read()!=124){
      moveX = 0;
      moveY = 0;
      Serial.println("FAILED");
   }
    
    Serial.println(moveX);
    Serial.println(moveY);
    Serial.println("DONE");
  }
  
  turn.writeMicroseconds(moveX*10+1500);
  up1.writeMicroseconds(moveY*10+1500);
  up2.writeMicroseconds(moveY*-10+1500);
}
