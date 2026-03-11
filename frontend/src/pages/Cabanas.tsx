import { useMemo, useState } from 'react'
import { Cabana } from '../data/models'
import { addCabana, listCabanas, toggleCabana } from '../data/store'

function useCabanas() {
  const [tick, setTick] = useState(0)
  const cabanas = useMemo(() => listCabanas(), [tick])
  function refresh() { setTick(x => x + 1) }
  return { cabanas, refresh }
}

export default function Cabanas() {
  const { cabanas, refresh } = useCabanas()
  const [cap, setCap] = useState<number | 'all'>('all')
  const [tipo, setTipo] = useState<'all' | Cabana['tipo']>('all')

  const filtradas = cabanas.filter(c => (cap === 'all' || c.capacidad >= cap) && (tipo === 'all' || c.tipo === tipo))

  const [nombre, setNombre] = useState('')
  const [capacidad, setCapacidad] = useState(2)
  const [precio, setPrecio] = useState(60)
  const [nuevoTipo, setNuevoTipo] = useState<Cabana['tipo']>('Estandar')

  function crear() {
    if (!nombre.trim()) return
    addCabana({ nombre: nombre.trim(), capacidad, precioNoche: precio, tipo: nuevoTipo, activa: true })
    setNombre('')
    setCapacidad(2)
    setPrecio(60)
    setNuevoTipo('Estandar')
    refresh()
  }

  function toggle(id: string, activa: boolean) {
    toggleCabana(id, activa)
    refresh()
  }

  return (
    <div className="grid">
      <div className="panel" style={{gridColumn: 'span 12'}}>
        <div className="section-title">
          <div style={{fontWeight: 700}}>Cabañas</div>
          <div style={{display: 'flex', gap: 8}}>
            <select value={cap} onChange={e => setCap(e.target.value === 'all' ? 'all' : parseInt(e.target.value))}>
              <option value="all">Todas capacidades</option>
              <option value="2">2 o más</option>
              <option value="4">4 o más</option>
              <option value="6">6 o más</option>
            </select>
            <select value={tipo} onChange={e => setTipo(e.target.value as any)}>
              <option value="all">Todos tipos</option>
              <option value="Estandar">Estandar</option>
              <option value="Gold">Gold</option>
              <option value="Premium">Premium</option>
            </select>
          </div>
        </div>
        <div className="list">
          {filtradas.map(c => (
            <div className="item" key={c.id}>
              <div>
                <div style={{fontWeight: 600}}>{c.nombre}</div>
                <div className="muted">{c.tipo} • {c.capacidad} pax</div>
              </div>
              <div>${c.precioNoche}/noche</div>
              <div>
                <span className={'badge ' + (c.activa ? 'ok' : 'danger')}>{c.activa ? 'Activa' : 'Inactiva'}</span>
              </div>
              <div>
                <button className="btn" onClick={() => toggle(c.id, !c.activa)}>{c.activa ? 'Desactivar' : 'Activar'}</button>
              </div>
            </div>
          ))}
          {filtradas.length === 0 && <div className="muted">Sin resultados</div>}
        </div>
      </div>
      <div className="panel" style={{gridColumn: 'span 12'}}>
        <div className="section-title">
          <div style={{fontWeight: 700}}>Nueva cabaña</div>
        </div>
        <div className="form">
          <input placeholder="Nombre" value={nombre} onChange={e => setNombre(e.target.value)} />
          <select value={capacidad} onChange={e => setCapacidad(parseInt(e.target.value))}>
            <option value={2}>2</option>
            <option value={4}>4</option>
            <option value={6}>6</option>
            <option value={8}>8</option>
          </select>
          <input type="number" min={1} step={1} placeholder="Precio/noche" value={precio} onChange={e => setPrecio(parseInt(e.target.value || '0'))} />
          <select value={nuevoTipo} onChange={e => setNuevoTipo(e.target.value as Cabana['tipo'])}>
            <option value="Estandar">Estandar</option>
            <option value="Gold">Gold</option>
            <option value="Premium">Premium</option>
          </select>
          <div />
          <button className="btn primary" onClick={crear}>Crear</button>
        </div>
      </div>
    </div>
  )
}
