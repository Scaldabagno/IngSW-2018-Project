# Query per ogni schermata

Sono query provvisorie

## Schermata Principale

```sql
# Per la lista di ogni Linea
SELECT *
FROM Linea;
```

## Login

```sql
# Per vedere se combaciano i dati, valido per i vari tipi di Impiegato, dipende dal caso
SELECT *
FROM AddettoAlPersonale/AddettoAiMezzi/Autista
WHERE (email = email AND password = password);
```

## Area Addetto Al Personale

### Lista di tutti gli impiegati

```sql
# Per lista di Impiegati di qualunque ruolo(?)
SELECT *
FROM Impiegati;
```

### Aggiungi nuovo Impiegato

```sql
# Per aggiunta di impiegato (in Java implementare con un if per vedere se esiste già)
INSERT INTO Impiegati("", ..., "") VALUES ("", ..., ""); # Da Fare
```

### Rimuovi Impiegato

```sql
# Per rimuovere un impiegato
DELETE FROM Impiegati WHERE(email = email AND password = password);
```

### Modifica Stipendio

```sql
UPDATE Impiegati
SET stipendio = stipendio
WHERE(matricola = matricola);
```

### Modifica Impiegato

```sql
UPDATE Impiegati
SET ... = ...
WHERE(matricola = matricola); # Da Fare
```

### Da completare