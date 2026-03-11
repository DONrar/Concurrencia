juan sebastian melendez sanchez
camilo andres acosta hernandez

Paralelismo vs Concurrencia → el paralelismo ejecuta tareas al mismo tiempo en núcleos físicos distintos (Ejercicio 1); la concurrencia gestiona múltiples tareas que compiten por el mismo recurso, no necesariamente simultáneas (Ejercicio 2).
Problema de acceso compartido → sin control, varios hilos pueden leer el saldo antes de que otro lo actualice, produciendo una Race Condition: los tres clientes ven saldo $1000, todos aprueban el retiro y el saldo termina en -200.
Race Condition → ocurre cuando el resultado de un programa depende del orden de ejecución de los hilos. Es no determinista y difícil de reproducir.
Por qué sincronizar → synchronized garantiza exclusión mutua: solo un hilo entra al método a la vez, asegurando que la lectura y escritura del saldo sean una operación atómica indivisible.
