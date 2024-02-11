
# Pollen Checker App

Checks the current pollen count.


### Implementation details

#### Dockerisation Commands

```
docker build -t pollencheck .
docker run -d --name pollencheck -p 7070:7070 pollencheck
```