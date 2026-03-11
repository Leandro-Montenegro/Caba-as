import { Routes, Route, NavLink, Navigate } from 'react-router-dom'
import Dashboard from './pages/Dashboard'
import Cabanas from './pages/Cabanas'
import Reservas from './pages/Reservas'
import './styles.css'

function Navbar() {
  return (
    <header>
      <div className="container">
        <div className="nav">
          <div className="brand">Cabañas</div>
          <NavLink to="/" className={({isActive}) => isActive ? 'btn primary' : 'btn'}>Inicio</NavLink>
          <NavLink to="/cabanas" className={({isActive}) => isActive ? 'btn primary' : 'btn'}>Cabañas</NavLink>
          <NavLink to="/reservas" className={({isActive}) => isActive ? 'btn primary' : 'btn'}>Reservas</NavLink>
          <div className="spacer" />
          <a className="btn" href="https://react.dev" target="_blank" rel="noreferrer">Ayuda</a>
        </div>
      </div>
    </header>
  )
}

export default function App() {
  return (
    <>
      <Navbar />
      <div className="container content">
        <Routes>
          <Route index element={<Dashboard />} />
          <Route path="/cabanas" element={<Cabanas />} />
          <Route path="/reservas" element={<Reservas />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </>
  )
}
