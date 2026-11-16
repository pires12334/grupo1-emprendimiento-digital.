# Bitácora del proyecto

## Día 1 (10/11/2026)
- Qué hicimos: armamos el grupo, creamos el tablero en Trello y elegimos un primer tema (Clase 8: Sets, Queues y Stacks – Clínica).
- Quién hizo qué: Mateo Aramayo selecciono el tema y hizo el readme de la clinica 
- Qué nos trabó: la profe nos dijo que el tema estaba ocupado
- Qué sigue: volver a hacerlo todo de vuelta

  
## Día 2 (11/11/2026)
- Qué hicimos: cambiamos el tema a **Clase 10: Archivos de texto y CSV** y el emprendimiento a un **gimnasio**, porque encaja mejor con el tema y es más acotado para el tiempo que tenemos. Actualizamos la planilla del curso, creamos el repositorio con la estructura de carpetas, el README y el análisis inicial.
- Quién hizo qué: Alan – repositorio, README, bitácora y análisis.
                  Mateo - Screen subidas en Kanban y descripción de las tarjetas en Trello. Y creación de la carpeta docs                              con el análisis de requerimiento.              
- Qué nos trabó: el cambio de tema y la demora en tener el repositorio compartido.
- Qué sigue: diseño (diagrama de clases y columnas de cada archivo CSV).

## Día 3 (12/11/2026)
- Qué hicimos: creamos el proyecto Maven, agregamos opencsv al pom.xml y empezamos la clase Socio (atributos, constructor, getters, setter y tieneCuotaVencida).
- Quién hizo qué: Alan – proyecto Maven y clase Socio.
- Quién hizo qué: Mateo - proyecto CSV, clase Socio (toString) y clase Pago.
- Qué sigue: terminar excepciones y lectura/escritura de CSV.
  
## Dia 4 (13/11/2026)
- Que nos trabo: hicimos dos análisis de negocio y por el mismo motivo dos clases socios. cero comunicacion.
- Que sigue: determinar que vamos a hacer al final.
- Finalmente logramos coordinarnos y unificar el análisis de negocio. Avanzamos con la estructura del proyecto CSV y la clase socio.
- Quién hizo qué: Mateo- Proyecto CSV, clase socio (toString) y código base
- Qué sigue: Completar la clase pagos y asistencias. Definir la versión final del alcance, terminar las excepciones y completar la lectura/escritura de archivos CSV.
  
## Día 5 (14/11/2026)
-Qué hicimos: Creamos en docs el diseño del sistema que nos había faltado por subir. Completamos la clase Pago y su gestión de archivos (ArchivoPagos), desarrollamos la clase ArchivoSocios (y Socios), implementamos las excepciones personalizadas (DatoInvalido, SocioDuplicado, SocioNoEcontrado) y armamos el menú/flujo en Principal.
-Quién hizo qué: Diego- completo la clase pagos y las tres excepciones.
                 Mateo- completo la clase ArchivoSocios y Socios. Creeación del archivo de diseño del sistema.
                        Screen subidas en Kanban y descripción de las tarjetas en Trello.
-Qué sigue: Realizar pruebas integrales de lectura y escritura de archivos CSV, validar el manejo de todas las excepciones y ajustar detalles finales en la clase Principal y en la documentación (README.md).
-  Alan creó RegistroAsistencias (registra ingresos en asistencias.txt con FileWriter en modo append, valida y descuenta pases, lee el historial con BufferedReader).

## Día 6 (15/11/2026)
- Qué hicimos: integramos los tres módulos. Actualizamos el README al modelo de pases, unificamos la clase Socio, corregimos ArchivoSocios, limpiamos archivos duplicados y subimos el módulo de asistencias y el menú principal. El proyecto compila y funciona completo.
- Quién hizo qué: Alan – actualizó el README (modelo de pases, módulos y responsables), subió RegistroAsistencias y Menu (menú en consola que une socios, pagos y asistencias, con importar y exportar) y borró Principal.java, que no dejaba compilar.
  Mateo – recuperó la clase Socio definitiva (5 datos) y corrigió ArchivoSocios con OpenCSV (5 columnas, excepciones propias, importar y exportar CSV).
  Diego – corrigió los packages a gimnasio.modelo, agregó la creación de la carpeta datos y renombró pagos.csv a movimientos.csv en ArchivoPagos, y borró module-info.java.
- Qué nos trabó: había clases duplicadas (dos Socio, dos menús, dos módulos de asistencias) por falta de comunicación, packages que no coincidían con las carpetas y cada uno trabajaba sobre una copia distinta del proyecto. Acordamos trabajar siempre sobre la versión del repositorio.
- Qué sigue: completar las pruebas, capturas del programa, README final y preparar la defensa.
