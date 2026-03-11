import { Cabana, Reserva, DataSnapshot } from './models'
import { initialData } from './mock'

const KEY = 'cabanias:data'

function uid(): string {
  const g: any = globalThis as any
  const rand = g?.crypto?.randomUUID?.()
  if (rand) return rand
  return 'id-' + Math.random().toString(36).slice(2) + '-' + Date.now().toString(36)
}

function read(): DataSnapshot {
  const raw = localStorage.getItem(KEY)
  if (!raw) {
    localStorage.setItem(KEY, JSON.stringify(initialData))
    return initialData
  }
  try {
    return JSON.parse(raw) as DataSnapshot
  } catch {
    localStorage.setItem(KEY, JSON.stringify(initialData))
    return initialData
  }
}

function write(data: DataSnapshot) {
  localStorage.setItem(KEY, JSON.stringify(data))
}

export function listCabanas(): Cabana[] {
  return read().cabanas
}

export function addCabana(input: Omit<Cabana, 'id'>): Cabana {
  const data = read()
  const cabana: Cabana = { id: uid(), ...input }
  data.cabanas.push(cabana)
  write(data)
  return cabana
}

export function toggleCabana(id: string, activa: boolean) {
  const data = read()
  const c = data.cabanas.find(x => x.id === id)
  if (c) c.activa = activa
  write(data)
}

export function listReservas(): Reserva[] {
  return read().reservas
}

function overlaps(a: {desde: string, hasta: string}, b: {desde: string, hasta: string}) {
  const ad = new Date(a.desde).getTime()
  const ah = new Date(a.hasta).getTime()
  const bd = new Date(b.desde).getTime()
  const bh = new Date(b.hasta).getTime()
  return ad < bh && bd < ah
}

export function createReserva(input: Omit<Reserva, 'id' | 'estado' | 'total'>): {ok: true, reserva: Reserva} | {ok: false, error: string} {
  const data = read()
  const cabana = data.cabanas.find(c => c.id === input.cabanaId && c.activa)
  if (!cabana) return { ok: false, error: 'Cabaña no disponible' }
  const conflictos = data.reservas.filter(r => r.estado === 'confirmada' && r.cabanaId === input.cabanaId && overlaps(r, input))
  if (conflictos.length > 0) return { ok: false, error: 'Fechas no disponibles' }
  const noches = Math.max(1, Math.ceil((new Date(input.hasta).getTime() - new Date(input.desde).getTime()) / 86400000))
  const total = noches * cabana.precioNoche
  const reserva: Reserva = { id: uid(), estado: 'confirmada', total, ...input }
  data.reservas.push(reserva)
  write(data)
  return { ok: true, reserva }
}

export function cancelarReserva(id: string) {
  const data = read()
  const r = data.reservas.find(x => x.id === id)
  if (r) r.estado = 'cancelada'
  write(data)
}
