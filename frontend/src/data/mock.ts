import { DataSnapshot } from './models'

export const initialData: DataSnapshot = {
  cabanas: [
    { id: 'c1', nombre: 'Araucaria', capacidad: 2, precioNoche: 60, tipo: 'Estandar', activa: true },
    { id: 'c2', nombre: 'Coihue', capacidad: 4, precioNoche: 95, tipo: 'Gold', activa: true },
    { id: 'c3', nombre: 'Lenga', capacidad: 6, precioNoche: 140, tipo: 'Premium', activa: true }
  ],
  reservas: []
}
