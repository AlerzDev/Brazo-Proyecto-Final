//Librerias
#include <Servo.h>
#include <Stepper.h>
#define STEPS 200
//Se definen las variables de los sevo motores
Servo servoMotorPinza;
Servo servoMotorBrazoDerecho;
Servo servoMotorBrazoIzquierdo;
int motorPin1 = 8;  //pin8----1N4
int motorPin2 = 9;  //pin8----1N3
int motorPin3 = 10; //pin8----1N2
int motorPin4 = 11; //pin8----1N1

void setup() 
{
  Serial.begin(9600); 
  Serial.setTimeout(10);
  IniciarMotores();
}

void loop() 
{
  if (Serial.available() > 0) {
    
    String entrada = Serial.readString();
    Serial.print(entrada);
    int motor = getValue(entrada,'@',0).toInt();
    int grados = getValue(entrada,'@',1).toInt();
    Serial.print(motor);
    Serial.print(grados);
    switch (motor) 
    {
      case 1:
        servoMotorPinza.write(grados);
        break;
      case 2:
        servoMotorBrazoDerecho.write(grados);
        break;
      case 3:
        servoMotorBrazoIzquierdo.write(grados);
        break;
      case 4:
          if(grados == 1)
          {
            for (int i=0; i <= 50; i++){
                gira_antihorario(2); 
             }  
          }
          if(grados == 0)
          {
            for (int i=0; i <= 50; i++){
                gira_horario(2); 
             } 
          }
      break;
    }
  }
}

void gira_horario(int delayTime)
{
  digitalWrite(motorPin1, HIGH);
  digitalWrite(motorPin2, LOW);
  digitalWrite(motorPin3, LOW);
  digitalWrite(motorPin4, HIGH);
  delay(delayTime); 
  digitalWrite(motorPin1, LOW);
  digitalWrite(motorPin2, LOW);
  digitalWrite(motorPin3, HIGH);
  digitalWrite(motorPin4, HIGH);
  delay(delayTime);
  digitalWrite(motorPin1, LOW);
  digitalWrite(motorPin2, HIGH);
  digitalWrite(motorPin3, HIGH);
  digitalWrite(motorPin4, LOW);
  delay(delayTime);
  digitalWrite(motorPin1, HIGH); // Los pines se activan en secuencia
  digitalWrite(motorPin2, HIGH);
  digitalWrite(motorPin3, LOW);
  digitalWrite(motorPin4, LOW);
  delay(delayTime);
}

void gira_antihorario(int delayTime)
{
  digitalWrite(motorPin1, HIGH); // Los pines se activan en secuencia
  digitalWrite(motorPin2, HIGH);
  digitalWrite(motorPin3, LOW);
  digitalWrite(motorPin4, LOW);
  delay(delayTime);
  digitalWrite(motorPin1, LOW);
  digitalWrite(motorPin2, HIGH);
  digitalWrite(motorPin3, HIGH);
  digitalWrite(motorPin4, LOW);
  delay(delayTime);
  digitalWrite(motorPin1, LOW);
  digitalWrite(motorPin2, LOW);
  digitalWrite(motorPin3, HIGH);
  digitalWrite(motorPin4, HIGH);
  delay(delayTime);
  digitalWrite(motorPin1, HIGH);
  digitalWrite(motorPin2, LOW);
  digitalWrite(motorPin3, LOW);
  digitalWrite(motorPin4, HIGH);
  delay(delayTime);  
}

String getValue(String data, char separator, int index)
{
    int found = 0;
    int strIndex[] = { 0, -1 };
    int maxIndex = data.length() - 1;

    for (int i = 0; i <= maxIndex && found <= index; i++) {
        if (data.charAt(i) == separator || i == maxIndex) {
            found++;
            strIndex[0] = strIndex[1] + 1;
            strIndex[1] = (i == maxIndex) ? i+1 : i;
        }
    }
    return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

void IniciarMotores()
{
  //Servo motores
  servoMotorPinza.attach(7);
  servoMotorBrazoDerecho.attach(6);
  servoMotorBrazoIzquierdo.attach(5);
  servoMotorPinza.write(10);
  servoMotorBrazoDerecho.write(0);
  servoMotorBrazoIzquierdo.write(0);
  pinMode(motorPin1, OUTPUT); // Configuración de los PINes como salida digital
  pinMode(motorPin2, OUTPUT);
  pinMode(motorPin3, OUTPUT);
  pinMode(motorPin4, OUTPUT);
  //Motor a pasos
}

