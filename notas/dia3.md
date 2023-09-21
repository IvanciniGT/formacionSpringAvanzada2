
# Organización del código

Estoy trabajando en un sistema, pero...
Cuántos proyectos tenemos ahora mismo? 3

3 proyectos con su propia conf de maven..
Y por ende, deberían ser 3 REPOS DE GIT

En Java pelao (desde 1.9) tenemos el concepto de MODULO: module-info.java
															module "animalitos-servicio"
En maven tenemos también el concepto de módulo... y necesitamos utilizar
Tendremos un proyecto maven nuevo que será mi SISTEMA! -> MODULOS
En git también tenemos el concepto de módulo:
	git submodule add