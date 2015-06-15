# Oping
Oping, a simplistic descriptive language.

This repository will offer two ports of the same code, one in Python (for method development) and Java (the one that I'm looking for to use in my projects).

A simple yet powerful example is:

```
+ Image(MyFirstImage)
  - Position: 0, 0
  - Rotation: 0
  + Sprite
    - Texture: "res/images/sprite.jpg"
    - Tint: 255, 100, 100
  + Shader
    - Blur: 1.5
    - ColorShift: 25

+ Include
  - Source: "Slides/AnotherSlide"
  - Position: 0, 0
  - Rotation: 90
´´´

The code above will create an image, with the id MyFirstImage, inside that, declare 4 things, a position, with values 0, 0, the rotation as 0, and then two other things, each one will have attributes inside, and so on.

The language needs indentation, and every declaration is in a different line. Lines beggining with a + are branches, and lines with - are nodes (you can say that is not necessary because of indentation, but those signs make the language instant readable).
