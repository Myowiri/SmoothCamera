# **Smooth Camera**

<a href="https://www.youtube.com/watch?v=DDDIOx-Jm8g">
  <img src="https://img.youtube.com/vi/DDDIOx-Jm8g/0.jpg" alt="Smooth camera" width="100%">
</a>




SmoothCamera adds cinematic, smooth camera movement to Minecraft. It softens sudden motion, smooths perspective transitions, and enhances overall visual feel. Client-side.



## **⚙️ Configuration**

### **⌨️ In game command configuration:**



> ### **🔧 Main command**
>
> ```
> /smoothcamera
> ```
>
> **🔁 Toggle**
>
> Enable or disable the mod:
>
>
> ```
> /smoothcamera toggle
> ```
>
> ✈️ Teleport(Since 1.20)
>
> Enable or disable smooth teleport:
>
>
>```
> /smoothcamera SmoothTeleport
>```
>
> **🎯 Smoothness (camera)**
>
> Controls how smooth the camera movement is:
>
>
> ```
> /smoothcamera TPSmoothness <value>
> ```
>
> Range: 0.001 – 0.5
> Default: 0.5
> Lower = faster, higher = smoother
>
> **🎥 F5 Smoothness (third-person)**
>
> Controls smooth transition in third-person view:
>
>
> ```
> /smoothcamera F5Smoothness <value>
> ```
>
> Range: 0.001 – 0.1
> Default: 0.025
> Lower = faster, higher = smoother
> ✨ Features
> Smooth cinematic camera movement
> Ender pearl & teleport smoothing
> Smooth third-person (F5) transitions
> Adjustable via commands
> Lightweight & client-side (works on any server)


### **📄 Config file configuration:**


> Configuration file located in ***/config/smoothcamera.json**
>
> Default file structure:
>
> ```
> {
>   "enabled": true,
>   "smoothness": 0.1,
>   "smoothnessf5": 0.006
> }
> ```
