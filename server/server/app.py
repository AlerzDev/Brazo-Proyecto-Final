from flask import Flask
app = Flask(__name__)
from flask import json
from flask import Response
from flask import request
from flask import jsonify
import serial
import pyowm
from decimal import Decimal

from flask import render_template
from camera import Camera

port = "/dev/cu.usbmodemFD131"

ard = serial.Serial(port,9600,timeout=5)

apikey = "444e0032067ad19fa3be458acee9f17f"
owm = pyowm.OWM(apikey)

@app.route('/message/<msg>',methods = ['GET'])
def send_msg(msg):
  if request.method == 'GET':
    ard.write(msg.encode())
    return jsonify(
      success = True
    )

@app.route('/temp/<string:lo>/<string:la>',methods = ['GET'])
def get_temp(lo, la):
    if request.method == 'GET':
      obs = owm.weather_at_coords(float(la),float(lo))
      w = obs.get_weather()
      print "Temperatura:",w.get_temperature(unit='celsius')
      tempe = "Temp:"+str(w.get_temperature(unit='celsius'))
      ard.write(tempe.encode())
      return jsonify(
        success = True
      )

@app.route('/time_date/<string:lo>/<string:la>',methods = ['GET'])
def get_time_date(lo, la):
    if request.method == 'GET': 
      obs = owm.weather_at_coords(float(la),float(lo))
      w = obs.get_weather()
      print "Tiempo:",w.get_reference_time(timeformat='iso')
      time = "Fecha: "+ str(w.get_reference_time(timeformat='iso'))
      ard.write(time.encode())
      return "yes"


@app.route('/brazo/<string:motor>/<string:grados>',methods = ['GET'])
def accion_brazo(motor, grados):
    if request.method == 'GET': 
      result = motor+"@"+grados
      print(result)
      ard.write(result.encode())
      return jsonify(
        success = True
      )


def gen(camera):
  while True:
    frame = camera.get_frame()
    yield (b'--frame\r\n'
            b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')


@app.route('/client_viewo')
def index():
    """Video streaming home page."""
    return render_template('index.html')


def gen(camera):
    """Video streaming generator function."""
    while True:
        frame = camera.get_frame()
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')


@app.route('/video_feed')
def video_feed():
    """Video streaming route. Put this in the src attribute of an img tag."""
    return Response(gen(Camera()),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

