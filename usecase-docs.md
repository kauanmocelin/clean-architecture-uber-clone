## UC3 – Solicitar Corrida (Request Ride)

### Atores
- Passageiro

### Input
- `passenger_id` (UUID) – account_id do passageiro
- `from` (lat, long) – coordenadas de origem
- `to` (lat, long) – coordenadas de destino

### Output
- `ride_id` (UUID)

### Regras de Negócio
- Verificar se `account_id` possui `is_passenger = true`
- Verificar se já existe uma corrida para o passageiro em status diferente de `"completed"`  
  - Se existir, lançar erro **PENDENTE**
- Gerar um novo `ride_id` (UUID)
- Definir o status como `"requested"`
- Definir o campo `date` com a data/hora atual

### Exemplo de dados
```text
fromLat: -27.584095225788835
fromLong: -48.545022195325124
toLat: -27.496887588317275
toLong: -48.52224380857146
```

## UC4 – Obter Corrida (GetRide)

### Input
- `ride_id` (UUID)

### Output
- Todos os dados da corrida, incluindo:
  - `ride_id`
  - `passenger_id`
  - `driver_id` (pode ser `null` até ser aceito no UC de AcceptRide)
  - `from_lat`, `from_long`
  - `to_lat`, `to_long`
  - `status`
  - `fare`
  - `distance`
  - `date`

### Regras de Negócio
- Recuperar os dados da corrida a partir do `ride_id`
- Retornar os dados mesmo que `driver_id` ainda seja `null`

### Exemplo de Saída
```json
{
  "ride_id": "de305d54-75b4-431b-adb2-eb6b9e546014",
  "passenger_id": "abc123",
  "driver_id": null,
  "from_lat": -27.584095225788835,
  "from_long": -48.545022195325124,
  "to_lat": -27.496887588317275,
  "to_long": -48.52224380857146,
  "status": "requested",
  "fare": null,
  "distance": null,
  "date": "2025-06-14T15:00:00Z"
}
```
