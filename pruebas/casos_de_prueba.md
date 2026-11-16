# Casos de prueba – Gimnasio Digital

Plantilla del Anexo C de la consigna. Se ejecutan desde `src/main/java/gimnasio/Menu.java` (Run As → Java Application).
Las capturas de cada caso están en `pruebas/capturas_pruebas.docx`.

**Fecha de ejecución:** 16/11/2026
**Ejecutado por:** Diego Altamirano

## Socios

| Nº | Qué pruebo | Datos de entrada | Resultado esperado | Resultado obtenido | ¿Pasó? |
|----|------------|------------------|--------------------|--------------------|--------|
| 1 | Alta de socio válido | Opción 1: DNI 45658856, "fernando suarez", cuota 15000, 2 pases | Se guarda en `socios.csv` | "Socio registrado." | Sí |
| 2 | Alta con DNI repetido | Opción 1: DNI 45658856 otra vez | Error `SocioDuplicado`, no se guarda | "ERROR: Ya existe un socio con el DNI 45658856" | Sí |
| 3 | Alta con nombre vacío | Opción 1: DNI válido, nombre vacío | Error `DatoInvalido` | "ERROR: El DNI y el nombre son obligatorios." | Sí |
| 4 | Alta con DNI 0 | Opción 1: DNI 0 | Error `DatoInvalido` | "ERROR: El DNI y el nombre son obligatorios." | Sí |
| 5 | Buscar socio inexistente | Opción 2: DNI 56984456 | Informa que no existe | "No existe un socio con ese DNI." | Sí |

## Pagos

| Nº | Qué pruebo | Datos de entrada | Resultado esperado | Resultado obtenido | ¿Pasó? |
|----|------------|------------------|--------------------|--------------------|--------|
| 6 | Pago válido | Opción 6: DNI 45658856, compra 3 pases | Cobra la cuota y suma 3 pases (queda con 5) | "Monto a cobrar: $15000.0 – Pago registrado. Se acreditaron 3 pases." | Sí |
| 7 | Pago con monto 0, negativo o fecha vacía | `PruebaPagos` (casos 5, 6 y 7) | Error `DatoInvalido` en los tres | "11 aprobadas, 0 falladas" | Sí |

## Asistencias

| Nº | Qué pruebo | Datos de entrada | Resultado esperado | Resultado obtenido | ¿Pasó? |
|----|------------|------------------|--------------------|--------------------|--------|
| 8 | Ingreso con pases | Opción 7: DNI 45658856 (tenía 5) | Descuenta un pase y se anota en `asistencias.txt` | "Ingreso registrado: fernando suarez. Pases restantes: 4" | Sí |
| 9 | Ingreso sin pases | Alta DNI 44579038 "lopez muños" con 0 pases → Opción 7 | No entra, error `DatoInvalido` | "No puede ingresar: El socio lopez muños no tiene pases disponibles" | Sí |
| 10 | Ver historial | Opción 8: DNI 45658856 | Muestra el ingreso con fecha y pases restantes | "2026-11-16T01:40:39 – DNI 45658856 – fernando suarez – Pases restantes: 4" | Sí |

## Resumen

- Casos ejecutados: 10 / 10
- Pasaron: 10
- No pasaron: 0

---

Además, la prueba automática `src/main/java/gimnasio/pruebas/PruebaPagos.java` verifica el módulo de pagos y las excepciones (11 casos).
