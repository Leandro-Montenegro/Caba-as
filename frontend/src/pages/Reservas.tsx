import { useMemo, useState } from 'react'
import { Cabana, Reserva } from '../data/models'
import { createReserva, listCabanas, listReservas, cancelarReserva } from '../data/store'

function useData() {
  const [tick, setTick] = useState(0)
  const cabanas = useMemo(() => listCabanas().filter(c => c.activa), [tick])
  const reservas = useMemo(() => listReservas(), [tick])
  function refresh() { setTick(x => x + 1) }
  return { cabanas, reservas, refresh }
}

export default function Reservas() {
  const { cabanas, reservas, refresh } = useData()
  const [cabanaId, setCabanaId] = useState(cabanas[0]?.id || '')
  const [cliente, setCliente] = useState('')
  const [desde, setDesde] = useState('')
  const [hasta, setHasta] = useState('')
  const [error, setError] = useState<string | null>(null)

  function crear() {
    setError(null)
    if (!cabanaId || !cliente.trim() || ! MacroDateValid(desde, hasta)) {
      setError('Datos inválidos')
      return
    }
    const res = createReserva({ cabanaId, cliente: cliente.trim(), desde, hasta })
    if (res.ok) {
      setCliente('')
      setDesde('')
      setHasta('')
      refresh()
    } else {
      setError(res.error)
    }
  }

  function cancelar(id: string) {
    cancelarReserva(id)
    refresh()
  }

  return (
    <div className="grid">
      <div className="panel" style={{gridColumn: 'span 12'}}>
        <div className="section-title">
          <div style={{fontWeight: 700}}>Nueva reserva</div>
          {error && <div className="badge danger">{error}</div>}
        </div>
        <div className="form">
          <select value={cabanaId} onChange={e => setCabanaId(e.target.value)}>
            {cabanas.map(c => <option key={c.id} value={c.id}>{c.nombre} • {c.capacidad} pax • ${c.precioNoche}/n</option>)}
          </select>
          <input placeholder="Cliente" value={cliente} onChange={e => setCliente(e.target.value)} />
          <input type="date" value={desde} onChange={e => setDesde(e.target.value)} />
          <input type="date" value={hasta} onChange={e => setHasta(e.target.value)} />
          <div />
          <button className="btn primary" onClick={crear}>Confirmar</button>
        </div>
      </div>
      <div className="panel" style={{gridColumn: 'span 12'}}>
        <div className="section-title">
          <div style={{fontWeight: 700}}>Reservas</div>
        </div>
        <div className="list">
          {reservas.map(r => {
            const cab = cabanas.find(c => c.id === r.cabanaId) || listCabanas().find(c => c.id === r.cabanaId)
            return (
              <div className="item" key={r.id}>
                <div>
                  <div style={{fontWeight: 600}}>{cab?.nombre || r.cabanaId}</div>
                  <div className="muted">{r.desde} → {r.hasta}</div>
                </div>
                <div>${r.total}</div>
                <div>
                  <span className={'badge ' + (r.estado === 'confirmada' ? 'ok' : 'warn')}>{r.estado}</span>
                </div>
                <div>
                  {r.estado === 'confirmada' && <button className="btn danger" onClick={() => cancelar(r.id)}>Cancelar</button>}
                </div>
              </div>
            )
          })}
          {reservas.length === 0 && <div className="muted">No hay reservas</div>}
        </div>
      </div>
    </div>
  )
}

function MacroDateValid(desde: string, hasta: string) {
  if (!desde || !hasta) return false
  const d = new Date(desde).getTime()
  const h = new Date(hasta).getTime()
  return !Number.isNaN(d) && !Number.isNaN(h) && d < h
}
