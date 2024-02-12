
# Pollen Checker App

Checks the current pollen count.


### Implementation details

#### Dockerisation Commands

still building jars manually and copying across to targetsave - fix this.

```
docker build -t pollencheck .
docker run -d --name pollencheck -p 7070:7070 pollencheck
```