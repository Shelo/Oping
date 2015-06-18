# Oping
Oping, a simplistic descriptive language.

Oping is aimed to be as simplistic as possible, appealing to the eyes, easily understable by anyone. The main premisse is "Why this should be more complicated than this?", there's no need for fancy languages, just something simple, easily parseable and completelly functional.

As an example, here's a simple hierarchy:

```
+ MainThread
  - UUID: 4742da68-1604-11e5-b60b-1697f925ec7b
  - LogFile: "output.log"
  - CreationDate: 18, 6, 2015
  + Subprocess
    - Id: 1
    + Target
      - File: "target1"
    + Target
      - File: "target2"
  + Subprocess
    - Id: 2
    + Target
      - File: "target3"
    + Target
      - File: "target4"
```

As you can see, there are very few rules, plus-signed lines indicate a branch, and minus-signed ones are leafs. It is very simple to see that this will translate very well to XML.

It is worth mentioning that in the file we can define lot of *MainThreads*, this is for convenience, so you don't have to have all in a big node. The language needs indentation, and every declaration is in a different line.
