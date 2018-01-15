#include<Servo.h>

int moveX;
int moveY;

Servo up;
Servo turn;

void setup(){
  Serial.begin(9600);
  up.attach(12);
  up.attach(13);
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
}
