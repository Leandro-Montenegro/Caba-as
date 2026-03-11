import { listCabanas, listReservas } from '../data/store'

export default function Dashboard() {
  const cabanas = listCabanas()
  const reservas = listReservas()
  const activas = cabanas.filter(c => c.activa).length
  const confirmadas = reservas.filter(r => r.estado === 'confirmada').length
  return (
    <div className="grid">
      <div className="panel" style={{gridColumn: 'span 12'}}>
        <div className="kpis">
          <div className="kpi">
            <div className="muted">Cabañas</div>
            <div style={{fontSize: 28, fontWeight: 700}}>{cabanas.length}</div>
          </div>
          <div className="kpi">
            <div className="muted">Activas</div>
            <div style={{fontSize: 28, fontWeight: 700}}>{activas}</div>
          </div>
          <div className="kpi">
            <div className="muted">Reservas</div>
            <div style={{fontSize: 28, fontWeight: 700}}>{confirmadas}</div>
          </div>
        </div>
      </div>
    </div>
  )
}
