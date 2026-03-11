export type Cabana = {
  id: string
  nombre: string
  capacidad: number
  precioNoche: number
  tipo: 'Estandar' | 'Gold' | 'Premium'
  activa: boolean
}

export type Reserva = {
  id: string
  cabanaId: string
  cliente: string
  desde: string
  hasta: string
  total: number
  estado: 'confirmada' | 'cancelada'
}

export type DataSnapshot = {
  cabanas: Cabana[]
  reservas: Reserva[]
}
