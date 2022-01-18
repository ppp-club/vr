import pyautogui
from pynput.mouse import Listener
import threading
import time
import json
import requests

pyautogui.FAILSAFE = True

ip = input("HOST: ")
port = input("PORT: ")

class mb:
    def __init__(self):
        self.mbutton = ""

mb.__init__(mb)

pos = {"x": 0, "y": 0}
minus = {"x": 0, "y": 0}

headers = {'Content-type': 'application/json'}

def updatePos(mbutton):
    if mbutton == "left":
        #(arduino abfrage + umrechnung) fürs erste per mousepos
        pos["x"] = pyautogui.position().x - minus["x"]
        pos["y"] = pyautogui.position().y - minus["y"]
        pos2 = json.dumps(pos)
        requests.post("http://"+ip+":"+port+"/sendcoords", pos2, headers=headers)
        print("http://"+ip+":"+port+"/sendcoords", pos2)
    if mbutton == "right":
        minus["x"] = pyautogui.position().x
        minus["y"] = pyautogui.position().y

    time.sleep(0.01)

def on_click(x, y, button, pressed):
    if pressed:
        if str(button) == "Button.left":
            mb.mbutton = "left"
        else:
            mb.mbutton = "right"
    else:
        mb.mbutton = ""

def run1():
    with Listener(on_click=on_click) as listener:
        listener.join()

print("Connecting to Server...")
thread1 = threading.Thread(target=run1)
thread1.start()
print("Successfully connected!")
while True:
    updatePos(mb.mbutton)