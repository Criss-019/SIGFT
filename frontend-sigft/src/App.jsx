import React, { useState } from 'react';
import {
  ShieldCheck,
  User,
  FileText,
  QrCode,
  CheckCircle,
  XCircle,
  Search,
  LogOut,
  AlertTriangle,
  Globe,
  Clock,
  ArrowLeft,
  Sparkles,
  ChevronRight,
  Shield,
  Fingerprint,
  Leaf,
  Package
} from 'lucide-react';

const API_URL = import.meta.env.VITE_API_GATEWAY_URL || 'http://localhost:8080';

// ─── Componente: Hora actual ────────────────────────────────────────
function LiveClock() {
  const [time, setTime] = useState(new Date());
  React.useEffect(() => {
    const t = setInterval(() => setTime(new Date()), 1000);
    return () => clearInterval(t);
  }, []);
  return (
    <span className="tabular-nums">
      {time.toLocaleTimeString('es-CL', { hour: '2-digit', minute: '2-digit', second: '2-digit' })}
    </span>
  );
}

// ─── Componente: Radio Option ───────────────────────────────────────
function RadioGroup({ name, label, onChange, defaultNo = true }) {
  return (
    <div>
      <label className="field-label">{label}</label>
      <div className="flex gap-3">
        <label className="radio-option flex-1">
          <input type="radio" name={name} value="si" onChange={onChange} className="accent-indigo-400" />
          <span className="text-sm font-medium text-slate-200">Sí</span>
        </label>
        <label className="radio-option flex-1">
          <input type="radio" name={name} value="no" onChange={onChange} defaultChecked={defaultNo} className="accent-indigo-400" />
          <span className="text-sm font-medium text-slate-200">No</span>
        </label>
      </div>
    </div>
  );
}

// ─── Componente: Campo de formulario ────────────────────────────────
function FormField({ label, children }) {
  return (
    <div>
      <label className="field-label">{label}</label>
      {children}
    </div>
  );
}

// ─── VISTA: Seleccionar Rol ─────────────────────────────────────────
function VistaSeleccionRol({ setVista }) {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center p-6 relative overflow-hidden animate-fade-in">
      {/* Orbes decorativos */}
      <div className="gradient-orb w-96 h-96 bg-indigo-600 -top-32 -left-32" />
      <div className="gradient-orb w-80 h-80 bg-blue-700 -bottom-20 -right-20" />
      <div className="gradient-orb w-64 h-64 bg-violet-600 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2" />

      {/* Header */}
      <div className="text-center mb-12 relative z-10 animate-slide-up">
        <div className="flex items-center justify-center mb-6">
          <div className="relative">
            <div className="absolute inset-0 bg-indigo-500 blur-2xl opacity-40 rounded-full scale-150" />
            <div className="relative p-5 rounded-2xl" style={{background: 'linear-gradient(135deg, rgba(56,57,121,0.6) 0%, rgba(32,78,115,0.6) 100%)', border: '1px solid rgba(255,255,255,0.15)'}}>
              <ShieldCheck className="w-14 h-14 text-indigo-300" />
            </div>
          </div>
        </div>
        <h1 className="text-5xl font-extrabold tracking-tight mb-3"
          style={{background: 'linear-gradient(135deg, #e0e7ff 0%, #a5b4fc 50%, #818cf8 100%)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent'}}>
          SIGFT
        </h1>
        <p className="text-slate-400 text-lg font-medium">Sistema de Control y Gestión Fronteriza Terrestre</p>
        <div className="flex items-center justify-center gap-4 mt-4 text-xs text-slate-500">
          <span className="flex items-center gap-1.5"><Globe className="w-3 h-3" /> Chile — Paso Fronterizo</span>
          <span className="w-1 h-1 rounded-full bg-slate-600" />
          <span className="flex items-center gap-1.5"><Clock className="w-3 h-3" /><LiveClock /></span>
        </div>
      </div>

      {/* Cards de roles */}
      <div className="grid md:grid-cols-2 gap-5 w-full max-w-3xl relative z-10">
        {/* Viajero */}
        <button
          onClick={() => setVista('portal-viajero')}
          className="group text-left p-8 rounded-3xl transition-all duration-300 hover:-translate-y-1 animate-slide-up"
          style={{
            background: 'linear-gradient(135deg, rgba(56,57,121,0.4) 0%, rgba(32,78,115,0.3) 100%)',
            border: '1px solid rgba(99,102,241,0.2)',
            boxShadow: '0 0 0 0 rgba(99,102,241,0.4)',
            animationDelay: '0.1s'
          }}
          onMouseEnter={e => e.currentTarget.style.boxShadow = '0 20px 60px rgba(99,102,241,0.25)'}
          onMouseLeave={e => e.currentTarget.style.boxShadow = '0 0 0 0 rgba(99,102,241,0.4)'}
        >
          <div className="icon-glow-blue inline-flex mb-5">
            <User className="w-7 h-7 text-indigo-300" />
          </div>
          <h2 className="text-2xl font-bold text-white mb-2 flex items-center justify-between">
            Soy Viajero
            <ChevronRight className="w-5 h-5 text-indigo-400 opacity-0 group-hover:opacity-100 transition-opacity" />
          </h2>
          <p className="text-slate-400 text-sm leading-relaxed">Generar tu declaración jurada anticipada y obtener el código QR para el cruce fronterizo.</p>
          <div className="mt-5 flex gap-2 flex-wrap">
            <span className="text-xs px-2 py-1 rounded-md text-indigo-400 font-medium" style={{background:'rgba(99,102,241,0.1)'}}>Declaración SAG</span>
            <span className="text-xs px-2 py-1 rounded-md text-indigo-400 font-medium" style={{background:'rgba(99,102,241,0.1)'}}>Código QR</span>
          </div>
        </button>

        {/* Funcionario */}
        <button
          onClick={() => setVista('portal-funcionario')}
          className="group text-left p-8 rounded-3xl transition-all duration-300 hover:-translate-y-1 animate-slide-up"
          style={{
            background: 'linear-gradient(135deg, rgba(5,150,105,0.2) 0%, rgba(6,95,70,0.15) 100%)',
            border: '1px solid rgba(16,185,129,0.2)',
            animationDelay: '0.2s'
          }}
          onMouseEnter={e => e.currentTarget.style.boxShadow = '0 20px 60px rgba(16,185,129,0.2)'}
          onMouseLeave={e => e.currentTarget.style.boxShadow = 'none'}
        >
          <div className="icon-glow-emerald inline-flex mb-5">
            <ShieldCheck className="w-7 h-7 text-emerald-400" />
          </div>
          <h2 className="text-2xl font-bold text-white mb-2 flex items-center justify-between">
            Funcionario
            <ChevronRight className="w-5 h-5 text-emerald-400 opacity-0 group-hover:opacity-100 transition-opacity" />
          </h2>
          <p className="text-slate-400 text-sm leading-relaxed">Acceso para PDI, SAG y Aduanas. Validación en tiempo real de pasajeros y escaneo de QR.</p>
          <div className="mt-5 flex gap-2 flex-wrap">
            <span className="text-xs px-2 py-1 rounded-md text-emerald-400 font-medium" style={{background:'rgba(16,185,129,0.1)'}}>PDI</span>
            <span className="text-xs px-2 py-1 rounded-md text-emerald-400 font-medium" style={{background:'rgba(16,185,129,0.1)'}}>SAG</span>
            <span className="text-xs px-2 py-1 rounded-md text-emerald-400 font-medium" style={{background:'rgba(16,185,129,0.1)'}}>Aduanas</span>
          </div>
        </button>
      </div>

      <p className="mt-10 text-xs text-slate-600 relative z-10">
        © {new Date().getFullYear()} SIGFT — Gobierno de Chile · Versión 2.0
      </p>
    </div>
  );
}

// ─── VISTA: Portal Viajero ──────────────────────────────────────────
function VistaPortalViajero({ setVista, setDatosDeclaracion, setCodigoGenerado }) {
  const [formData, setFormData] = useState({
    rut: '', nombres: '', apellidos: '', edad: '',
    nacionalidad: '', email: '', patente: '',
    traeMenores: 'no', traeAnimales: 'no', traeVegetales: 'no', poseeMascotas: 'no'
  });
  const [isLoading, setIsLoading] = useState(false);
  const [step, setStep] = useState(1);

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      await fetch(`${API_URL}/api/v1/pasajeros`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          rut: formData.rut,
          nombre: `${formData.nombres} ${formData.apellidos}`,
          edad: parseInt(formData.edad),
          nacionalidad: formData.nacionalidad,
          email: formData.email
        })
      });
      const idDeclaracion = `SAG-${Date.now()}`;
      await fetch(`${API_URL}/api/v1/declaraciones-sag`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          idDeclaracion,
          fechaRegistro: new Date().toISOString(),
          traeProductosAnimales: formData.traeAnimales === 'si',
          traeProductosVegetales: formData.traeVegetales === 'si',
          poseeMascotas: formData.poseeMascotas === 'si',
          rutPasajero: formData.rut
        })
      });
      setDatosDeclaracion(formData);
      setCodigoGenerado(idDeclaracion);
      setVista('qr-generado');
    } catch {
      alert('Error de conexión con los microservicios. Verifique la red.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen p-4 md:p-8 relative animate-fade-in">
      <div className="gradient-orb w-80 h-80 bg-indigo-700 -top-20 -right-20 opacity-15" />

      {/* Navbar */}
      <div className="max-w-2xl mx-auto flex items-center justify-between mb-8">
        <button onClick={() => setVista('seleccionar-rol')} className="btn-secondary">
          <ArrowLeft className="w-4 h-4" /> Inicio
        </button>
        <div className="flex items-center gap-2 text-xs text-slate-500">
          <div className={`w-2 h-2 rounded-full ${step >= 1 ? 'bg-indigo-400' : 'bg-slate-700'}`} />
          <div className={`w-8 h-px ${step >= 2 ? 'bg-indigo-400' : 'bg-slate-700'}`} />
          <div className={`w-2 h-2 rounded-full ${step >= 2 ? 'bg-indigo-400' : 'bg-slate-700'}`} />
          <div className={`w-8 h-px bg-slate-700`} />
          <div className="w-2 h-2 rounded-full bg-slate-700" />
          <span className="ml-2">Paso {step} de 3</span>
        </div>
      </div>

      <div className="max-w-2xl mx-auto">
        {/* Header card */}
        <div className="glass p-6 mb-5 flex items-center gap-4 animate-slide-up">
          <div className="icon-glow-blue">
            <FileText className="w-6 h-6 text-indigo-300" />
          </div>
          <div>
            <h2 className="text-xl font-bold text-white">Declaración de Cruce Fronterizo</h2>
            <p className="text-sm text-slate-400">Complete todos los campos requeridos para generar su pase de cruce.</p>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4 animate-slide-up" style={{animationDelay:'0.1s'}}>
          {/* Sección 1: Identificación */}
          <div className="glass p-6">
            <h3 className="text-sm font-semibold text-indigo-300 uppercase tracking-widest mb-5 flex items-center gap-2">
              <Fingerprint className="w-4 h-4" /> Datos de Identificación
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <FormField label="RUT o Número de Pasaporte">
                <input required type="text" name="rut" onChange={handleChange}
                  className="input-field" placeholder="12.345.678-9" />
              </FormField>
              <FormField label="Patente del Vehículo (Opcional)">
                <input type="text" name="patente" onChange={handleChange}
                  className="input-field" placeholder="ABCD-12" />
              </FormField>
              <FormField label="Nombres">
                <input required type="text" name="nombres" onChange={handleChange} className="input-field" />
              </FormField>
              <FormField label="Apellidos">
                <input required type="text" name="apellidos" onChange={handleChange} className="input-field" />
              </FormField>
              <FormField label="Edad">
                <input required type="number" name="edad" min="0" max="120" onChange={handleChange} className="input-field" />
              </FormField>
              <FormField label="Nacionalidad">
                <input required type="text" name="nacionalidad" onChange={handleChange}
                  className="input-field" placeholder="Chilena" />
              </FormField>
              <div className="md:col-span-2">
                <FormField label="Correo Electrónico">
                  <input required type="email" name="email" onChange={handleChange}
                    className="input-field" placeholder="correo@ejemplo.com" />
                </FormField>
              </div>
            </div>
          </div>

          {/* Sección 2: Menores */}
          <div className="glass p-6">
            <h3 className="text-sm font-semibold text-indigo-300 uppercase tracking-widest mb-5 flex items-center gap-2">
              <User className="w-4 h-4" /> Acompañantes
            </h3>
            <RadioGroup name="traeMenores" label="¿Viaja con menores de edad?" onChange={handleChange} />
          </div>

          {/* Sección 3: SAG */}
          <div className="glass p-6" style={{border: '1px solid rgba(245,158,11,0.2)', background: 'rgba(245,158,11,0.04)'}}>
            <h3 className="text-sm font-semibold text-amber-400 uppercase tracking-widest mb-1 flex items-center gap-2">
              <Leaf className="w-4 h-4" /> Declaración Jurada SAG
            </h3>
            <p className="text-xs text-slate-500 mb-5">Declaro bajo juramento que la información proporcionada es verídica.</p>
            <div className="space-y-4">
              <RadioGroup name="traeAnimales" label="¿Trae productos de origen animal (carne, lácteos, huevos)?" onChange={handleChange} />
              <RadioGroup name="traeVegetales" label="¿Trae productos de origen vegetal (frutas, semillas, plantas)?" onChange={handleChange} />
              <RadioGroup name="poseeMascotas" label="¿Viaja con mascotas (perros, gatos, aves, hurones)?" onChange={handleChange} />
            </div>
          </div>

          <button type="submit" disabled={isLoading} className="btn-primary w-full py-4 text-base">
            {isLoading ? (
              <span className="flex items-center gap-3">
                <svg className="animate-spin w-5 h-5" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"/>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"/>
                </svg>
                Conectando con microservicios...
              </span>
            ) : (
              <span className="flex items-center gap-2">
                <Sparkles className="w-5 h-5" /> Generar Código QR de Cruce
              </span>
            )}
          </button>
        </form>
      </div>
    </div>
  );
}

// ─── VISTA: QR Generado ─────────────────────────────────────────────
function VistaQR({ setVista, codigoGenerado, datosDeclaracion }) {
  return (
    <div className="min-h-screen flex items-center justify-center p-6 relative animate-fade-in">
      <div className="gradient-orb w-96 h-96 bg-emerald-700 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 opacity-15" />

      <div className="max-w-md w-full animate-scale-in">
        <div className="glass p-8 text-center">
          {/* Ícono de éxito */}
          <div className="relative inline-flex mb-6">
            <div className="absolute inset-0 bg-emerald-500 blur-2xl opacity-30 rounded-full scale-150" />
            <div className="relative icon-glow-emerald p-2">
              <CheckCircle className="w-12 h-12 text-emerald-400" />
            </div>
          </div>

          <h2 className="text-2xl font-bold text-white mb-1">¡Declaración Exitosa!</h2>
          <p className="text-slate-400 text-sm mb-8">Presente este código en la ventanilla única fronteriza.</p>

          {/* QR placeholder */}
          <div className="relative mb-6">
            <div className="absolute inset-0 bg-indigo-500 opacity-10 blur-xl rounded-2xl" />
            <div className="relative p-8 rounded-2xl flex items-center justify-center"
              style={{background: 'rgba(255,255,255,0.05)', border: '2px dashed rgba(99,102,241,0.3)'}}>
              <QrCode className="w-36 h-36 text-indigo-300" />
            </div>
          </div>

          {/* ID */}
          <div className="p-3 rounded-xl mb-6 font-mono text-sm text-indigo-300"
            style={{background: 'rgba(99,102,241,0.1)', border: '1px solid rgba(99,102,241,0.2)'}}>
            {codigoGenerado}
          </div>

          {/* Datos resumidos */}
          {datosDeclaracion && (
            <div className="text-left p-4 rounded-xl mb-6 space-y-2"
              style={{background: 'rgba(255,255,255,0.03)', border: '1px solid rgba(255,255,255,0.07)'}}>
              <p className="text-xs text-slate-500 uppercase tracking-wider font-semibold mb-3">Resumen del Cruce</p>
              <div className="flex justify-between text-sm">
                <span className="text-slate-400">RUT</span>
                <span className="text-white font-medium">{datosDeclaracion.rut}</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-slate-400">Nombre</span>
                <span className="text-white font-medium">{datosDeclaracion.nombres} {datosDeclaracion.apellidos}</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-slate-400">Nacionalidad</span>
                <span className="text-white font-medium">{datosDeclaracion.nacionalidad}</span>
              </div>
            </div>
          )}

          <button onClick={() => setVista('seleccionar-rol')} className="btn-secondary w-full justify-center">
            <ArrowLeft className="w-4 h-4" /> Volver al Inicio
          </button>
        </div>
      </div>
    </div>
  );
}

// ─── VISTA: Funcionario ─────────────────────────────────────────────
function VistaFuncionario({ setVista }) {
  const [busqueda, setBusqueda] = useState('');
  const [resultado, setResultado] = useState(null);
  const [cargando, setCargando] = useState(false);

  const buscarViajero = async (e) => {
    e.preventDefault();
    setCargando(true);
    setResultado(null);
    try {
      const pasajeroRes = await fetch(`${API_URL}/api/v1/pasajeros/${busqueda}`);
      if (!pasajeroRes.ok) throw new Error('No encontrado');
      const pasajero = await pasajeroRes.json();

      const sagRes = await fetch(`${API_URL}/api/v1/declaraciones-sag/pasajero/${busqueda}`);
      let estadoSAG = 'Sin Declaración';
      if (sagRes.ok) {
        const decls = await sagRes.json();
        if (decls.length > 0) {
          estadoSAG = decls[0].requiereRevisionSAG ? 'Revisión Requerida' : 'Aprobado';
        }
      }

      setResultado({
        rut: pasajero.rut,
        nombre: pasajero.nombre,
        patente: pasajero.vehiculos?.length > 0 ? pasajero.vehiculos[0].patente : null,
        estadoPDI: 'Aprobado',
        estadoSAG,
        estadoAduana: 'Aprobado'
      });
    } catch {
      alert('No se encontró información para el RUT ingresado.');
    } finally {
      setCargando(false);
    }
  };

  const getVerifyProps = (estado) => {
    switch (estado) {
      case 'Aprobado':
        return { card: 'verify-card-success', icon: 'icon-glow-emerald', iconEl: <CheckCircle className="w-5 h-5 text-emerald-400" />, badge: 'badge-success' };
      case 'Revisión Requerida':
        return { card: 'verify-card-warning', icon: 'icon-glow-amber', iconEl: <AlertTriangle className="w-5 h-5 text-amber-400" />, badge: 'badge-warning' };
      case 'Sin Declaración':
        return { card: 'verify-card-neutral', icon: 'icon-glow-neutral', iconEl: <FileText className="w-5 h-5 text-slate-400" />, badge: 'badge-neutral' };
      default:
        return { card: 'verify-card-danger', icon: 'icon-glow-red', iconEl: <XCircle className="w-5 h-5 text-red-400" />, badge: 'badge-danger' };
    }
  };

  return (
    <div className="min-h-screen animate-fade-in">
      {/* Header */}
      <header className="sticky top-0 z-50 px-6 py-4"
        style={{background: 'rgba(10,14,26,0.8)', backdropFilter: 'blur(20px)', borderBottom: '1px solid rgba(255,255,255,0.07)'}}>
        <div className="max-w-5xl mx-auto flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="icon-glow-emerald">
              <ShieldCheck className="w-5 h-5 text-emerald-400" />
            </div>
            <div>
              <h1 className="font-bold text-white leading-none">SIGFT Ventanilla Única</h1>
              <p className="text-xs text-slate-500 mt-0.5">Portal de Verificación de Cruce</p>
            </div>
          </div>
          <div className="flex items-center gap-3">
            <span className="hidden md:flex items-center gap-1.5 text-xs text-slate-500">
              <Clock className="w-3 h-3" /> <LiveClock />
            </span>
            <button onClick={() => setVista('seleccionar-rol')} className="btn-secondary text-sm">
              <LogOut className="w-4 h-4" /> Salir
            </button>
          </div>
        </div>
      </header>

      <main className="p-4 md:p-8 max-w-5xl mx-auto">
        {/* Buscador */}
        <div className="glass p-6 mb-6 animate-slide-up">
          <h2 className="text-sm font-semibold text-slate-300 uppercase tracking-widest mb-4 flex items-center gap-2">
            <QrCode className="w-4 h-4 text-emerald-400" /> Escanear QR o Ingresar Documento
          </h2>
          <form onSubmit={buscarViajero} className="flex gap-3">
            <div className="relative flex-1">
              <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 w-5 h-5" />
              <input
                type="text" value={busqueda} required
                onChange={(e) => setBusqueda(e.target.value)}
                placeholder="Ingrese RUT o código SIGFT..."
                className="input-field pl-12"
              />
            </div>
            <button type="submit" disabled={cargando}
              className="px-6 py-3 rounded-xl font-semibold text-white transition-all duration-200 flex items-center gap-2 disabled:opacity-50"
              style={{background: 'linear-gradient(135deg, #059669, #047857)', boxShadow: '0 4px 20px rgba(5,150,105,0.4)'}}>
              {cargando ? (
                <svg className="animate-spin w-5 h-5" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"/>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"/>
                </svg>
              ) : <QrCode className="w-5 h-5" />}
              {cargando ? 'Consultando...' : 'Verificar'}
            </button>
          </form>
        </div>

        {/* Resultado */}
        {resultado && (
          <div className="grid md:grid-cols-3 gap-5 animate-slide-up">
            {/* Ficha del pasajero */}
            <div className="glass p-6">
              <h3 className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-5 flex items-center gap-2">
                <User className="w-3.5 h-3.5" /> Datos del Viajero
              </h3>
              <div className="space-y-4">
                <div>
                  <p className="text-xs text-slate-500 mb-1">RUT / Pasaporte</p>
                  <p className="font-bold text-white text-lg font-mono">{resultado.rut}</p>
                </div>
                <div>
                  <p className="text-xs text-slate-500 mb-1">Nombre Completo</p>
                  <p className="font-semibold text-slate-200">{resultado.nombre}</p>
                </div>
                {resultado.patente && (
                  <div>
                    <p className="text-xs text-slate-500 mb-1">Vehículo</p>
                    <span className="font-mono text-sm font-bold px-3 py-1 rounded-lg text-white"
                      style={{background:'rgba(255,255,255,0.1)', border:'1px solid rgba(255,255,255,0.15)'}}>
                      {resultado.patente}
                    </span>
                  </div>
                )}
                <div className="pt-2 border-t border-white/5">
                  <span className="badge-success text-xs">
                    <CheckCircle className="w-3 h-3" /> Registro Activo
                  </span>
                </div>
              </div>
            </div>

            {/* Verificaciones */}
            <div className="md:col-span-2 space-y-3">
              {[
                { label: 'Policía de Investigaciones (PDI)', sub: 'Validación de arraigo y antecedentes penales', estado: resultado.estadoPDI, icon: <Shield className="w-5 h-5" /> },
                { label: 'Servicio Agrícola y Ganadero (SAG)', sub: 'Control fitozoosanitario de mercancías', estado: resultado.estadoSAG, icon: <Leaf className="w-5 h-5" /> },
                { label: 'Aduanas de Chile', sub: 'Control de franquicias y declaración de mercancías', estado: resultado.estadoAduana, icon: <Package className="w-5 h-5" /> },
              ].map(({ label, sub, estado, icon }) => {
                const props = getVerifyProps(estado);
                return (
                  <div key={label} className={`verify-card ${props.card}`}>
                    <div className="flex items-center gap-4">
                      <div className={props.icon}>{icon}</div>
                      <div>
                        <h4 className="font-semibold text-white text-sm">{label}</h4>
                        <p className="text-xs text-slate-400 mt-0.5">{sub}</p>
                      </div>
                    </div>
                    <span className={props.badge}>
                      {estado === 'Aprobado' && <CheckCircle className="w-3 h-3" />}
                      {estado === 'Revisión Requerida' && <AlertTriangle className="w-3 h-3" />}
                      {estado === 'Sin Declaración' && <FileText className="w-3 h-3" />}
                      {estado}
                    </span>
                  </div>
                );
              })}

              {/* Acción final */}
              <div className="pt-3 flex justify-end">
                <button className="px-8 py-3 rounded-xl font-bold text-white transition-all duration-200 flex items-center gap-2"
                  style={{background:'linear-gradient(135deg,#383979,#204E73)', boxShadow:'0 4px 20px rgba(56,57,121,0.4)'}}>
                  <CheckCircle className="w-5 h-5" /> Autorizar Cruce Fronterizo
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Estado vacío */}
        {!resultado && !cargando && (
          <div className="text-center py-20 animate-fade-in">
            <div className="icon-glow-neutral inline-flex mb-4 p-4">
              <QrCode className="w-10 h-10 text-slate-500" />
            </div>
            <p className="text-slate-400 font-medium">Ingrese un RUT o escanee un código QR</p>
            <p className="text-slate-600 text-sm mt-1">para verificar el estado del viajero en los sistemas.</p>
          </div>
        )}
      </main>
    </div>
  );
}

// ─── ROOT ───────────────────────────────────────────────────────────
export default function App() {
  const [vistaActual, setVistaActual] = useState('seleccionar-rol');
  const [datosDeclaracion, setDatosDeclaracion] = useState(null);
  const [codigoGenerado, setCodigoGenerado] = useState('');

  switch (vistaActual) {
    case 'portal-viajero':
      return <VistaPortalViajero setVista={setVistaActual} setDatosDeclaracion={setDatosDeclaracion} setCodigoGenerado={setCodigoGenerado} />;
    case 'qr-generado':
      return <VistaQR setVista={setVistaActual} codigoGenerado={codigoGenerado} datosDeclaracion={datosDeclaracion} />;
    case 'portal-funcionario':
      return <VistaFuncionario setVista={setVistaActual} />;
    default:
      return <VistaSeleccionRol setVista={setVistaActual} />;
  }
}