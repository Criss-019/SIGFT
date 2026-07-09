import React, { useState } from 'react';
import {
  ShieldCheck,
  User,
  FileText,
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
  Package,
  FileSpreadsheet
} from 'lucide-react';
import { QRCodeSVG } from 'qrcode.react';

const API_URL = import.meta.env.VITE_API_GATEWAY_URL || 'http://localhost:8080';

const limpiarRut = (rut) => {
  if (!rut) return '';
  let valor = rut.replace(/\./g, '').replace(/\s+/g, '').replace(/-/g, '');
  if (valor.length < 2) return valor;
  const cuerpo = valor.slice(0, -1);
  const dv = valor.slice(-1);
  return `${cuerpo}-${dv}`;
};

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
          <input type="radio" name={name} value="si" onChange={onChange} className="accent-indigo-600" />
          <span className="text-sm font-semibold text-slate-700">Sí</span>
        </label>
        <label className="radio-option flex-1">
          <input type="radio" name={name} value="no" onChange={onChange} defaultChecked={defaultNo} className="accent-indigo-600" />
          <span className="text-sm font-semibold text-slate-700">No</span>
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
      {/* Elementos decorativos institucionales */}
      <div className="absolute top-0 left-0 right-0 h-1.5" style={{ background: 'linear-gradient(90deg, #004481 0%, #0082c9 50%, #d81e05 100%)' }} />

      {/* Header */}
      <div className="text-center mb-10 relative z-10 animate-slide-up">
        <div className="flex items-center justify-center mb-5">
          <div className="relative">
            <div className="absolute inset-0 bg-blue-500/10 blur-2xl rounded-full scale-150" />
            <div className="relative p-5 rounded-3xl" style={{background: 'linear-gradient(135deg, #004481 0%, #002d56 100%)', boxShadow: '0 10px 25px rgba(0, 68, 129, 0.25)'}}>
              <ShieldCheck className="w-12 h-12 text-white" />
            </div>
          </div>
        </div>
        <h1 className="text-4xl font-black tracking-tight mb-2 text-slate-900" style={{ letterSpacing: '-0.03em' }}>
          SISTEMA <span style={{ color: '#004481' }}>SIGFT</span>
        </h1>
        <p className="text-slate-600 text-base font-medium max-w-md mx-auto">
          Control de Cruce e Integración de Tránsito Fronterizo Terrestre
        </p>
        <div className="flex items-center justify-center gap-3 mt-4 text-xs text-slate-500 font-semibold bg-white/60 backdrop-blur px-4 py-1.5 rounded-full border border-slate-200 inline-flex mx-auto">
          <span className="flex items-center gap-1.5 text-slate-600"><Globe className="w-3.5 h-3.5 text-blue-600" /> Aduana & Paso Fronterizo</span>
          <span className="w-1.5 h-1.5 rounded-full bg-slate-300" />
          <span className="flex items-center gap-1.5 text-slate-600"><Clock className="w-3.5 h-3.5 text-slate-500" /><LiveClock /></span>
        </div>
      </div>

      {/* Cards de roles */}
      <div className="grid md:grid-cols-2 gap-6 w-full max-w-3xl relative z-10">
        {/* Viajero */}
        <button
          onClick={() => setVista('portal-viajero')}
          className="role-card role-card-indigo flex flex-col justify-between animate-slide-up text-left"
          style={{ animationDelay: '0.1s' }}
        >
          <div>
            <div className="icon-wrap icon-indigo inline-flex mb-5">
              <User className="w-6 h-6" />
            </div>
            <h2 className="text-xl font-extrabold text-slate-900 mb-2 flex items-center justify-between">
              Declaración de Viajero
              <ChevronRight className="w-5 h-5 text-indigo-700" />
            </h2>
            <p className="text-slate-600 text-sm leading-relaxed">Complete su declaración jurada del Servicio Agrícola y Ganadero (SAG) antes de llegar al paso fronterizo.</p>
          </div>
          <div className="mt-8 flex gap-2 flex-wrap">
            <span className="badge badge-blue">Formulario Digital</span>
            <span className="badge badge-neutral">Obtener código QR</span>
          </div>
        </button>

        {/* Funcionario */}
        <button
          onClick={() => setVista('portal-funcionario')}
          className="role-card role-card-emerald flex flex-col justify-between animate-slide-up text-left"
          style={{ animationDelay: '0.2s' }}
        >
          <div>
            <div className="icon-wrap icon-emerald inline-flex mb-5">
              <ShieldCheck className="w-6 h-6" />
            </div>
            <h2 className="text-xl font-extrabold text-slate-900 mb-2 flex items-center justify-between">
              Control Fronterizo (Funcionario)
              <ChevronRight className="w-5 h-5 text-emerald-700" />
            </h2>
            <p className="text-slate-600 text-sm leading-relaxed">Herramienta de ventanilla única para oficiales de PDI, SAG y Aduanas. Inspección y aprobación de pasajeros.</p>
          </div>
          <div className="mt-8 flex gap-2 flex-wrap">
            <span className="badge badge-success">Ventanilla Única</span>
            <span className="badge badge-neutral">Escanear QR</span>
          </div>
        </button>
      </div>

      <p className="mt-16 text-xs font-semibold text-slate-500 relative z-10">
        © {new Date().getFullYear()} SIGFT · Unidad de Control Fronterizo · Gobierno de Chile
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

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const rutLimpio = limpiarRut(formData.rut);
      
      const pasajeroRes = await fetch(`${API_URL}/api/v1/pasajeros`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          rut: rutLimpio,
          nombre: `${formData.nombres} ${formData.apellidos}`,
          edad: parseInt(formData.edad),
          nacionalidad: formData.nacionalidad,
          email: formData.email
        })
      });

      if (!pasajeroRes.ok) {
        const errorData = await pasajeroRes.json();
        const msg = errorData.rut || errorData.error || 'Error al registrar el pasajero.';
        alert(`Error de validación: ${msg}`);
        setIsLoading(false);
        return;
      }

      const idDeclaracion = `SAG-${Date.now()}`;
      const sagRes = await fetch(`${API_URL}/api/v1/declaraciones-sag`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          idDeclaracion,
          fechaRegistro: new Date().toISOString(),
          traeProductosAnimales: formData.traeAnimales === 'si',
          traeProductosVegetales: formData.traeVegetales === 'si',
          poseeMascotas: formData.poseeMascotas === 'si',
          rutPasajero: rutLimpio
        })
      });

      if (!sagRes.ok) {
        const errorData = await sagRes.json();
        const msg = errorData.error || 'Error al registrar declaración SAG.';
        alert(`Error SAG: ${msg}`);
        setIsLoading(false);
        return;
      }

      const datosEnviados = { ...formData, rut: rutLimpio };
      setDatosDeclaracion(datosEnviados);
      setCodigoGenerado(idDeclaracion);
      setVista('qr-generado');
    } catch (err) {
      console.error(err);
      alert('Error de conexión con los microservicios. Verifique la red.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen p-4 md:p-8 relative animate-fade-in">
      <div className="absolute top-0 left-0 right-0 h-1.5" style={{ background: 'linear-gradient(90deg, #004481 0%, #0082c9 50%, #d81e05 100%)' }} />

      {/* Navbar */}
      <div className="max-w-2xl mx-auto flex items-center justify-between mb-8">
        <button onClick={() => setVista('seleccionar-rol')} className="btn-secondary">
          <ArrowLeft className="w-4 h-4" /> Volver al Inicio
        </button>
        <div className="flex items-center gap-3">
          <div className="step-dot step-dot-active">1</div>
          <div className="w-6 h-[2px] bg-slate-300" />
          <div className="step-dot step-dot-active">2</div>
          <div className="w-6 h-[2px] bg-slate-300" />
          <div className="step-dot step-dot-idle">3</div>
        </div>
      </div>

      <div className="max-w-2xl mx-auto">
        {/* Header card */}
        <div className="glass p-6 mb-6 flex items-center gap-4 animate-slide-up">
          <div className="icon-wrap icon-indigo">
            <FileText className="w-6 h-6" />
          </div>
          <div>
            <h2 className="text-xl font-extrabold text-slate-900">Declaración de Cruce Fronterizo</h2>
            <p className="text-sm text-slate-500">Formulario obligatorio de declaración jurada conjunta (SAG / ADUANA).</p>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6 animate-slide-up" style={{animationDelay:'0.1s'}}>
          {/* Sección 1: Identificación */}
          <div className="glass p-6 space-y-4">
            <h3 className="section-header section-header-indigo">
              <Fingerprint className="w-4.5 h-4.5" /> Datos de Identificación
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <FormField label="RUT o Número de Pasaporte">
                <input required type="text" name="rut" onChange={handleChange}
                  className="input-field" placeholder="Ej: 12.345.678-9" />
              </FormField>
              <FormField label="Patente del Vehículo (Si aplica)">
                <input type="text" name="patente" onChange={handleChange}
                  className="input-field" placeholder="Ej: ABCD-12" />
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
                  className="input-field" placeholder="Ej: Chilena" />
              </FormField>
              <div className="md:col-span-2">
                <FormField label="Correo Electrónico">
                  <input required type="email" name="email" onChange={handleChange}
                    className="input-field" placeholder="Ej: nombre@correo.com" />
                </FormField>
              </div>
            </div>
          </div>

          {/* Sección 2: Acompañantes */}
          <div className="glass p-6">
            <h3 className="section-header section-header-indigo">
              <User className="w-4.5 h-4.5" /> Acompañantes
            </h3>
            <RadioGroup name="traeMenores" label="¿Viaja acompañado de menores de edad?" onChange={handleChange} />
          </div>

          {/* Sección 3: SAG */}
          <div className="glass p-6" style={{border: '1px solid rgba(245,158,11,0.25)', background: 'rgba(245,158,11,0.02)'}}>
            <h3 className="section-header section-header-amber">
              <Leaf className="w-4.5 h-4.5" /> Declaración Jurada Silvoagropecuaria (SAG)
            </h3>
            <p className="text-xs text-slate-500 mb-4">Deberá declarar si transporta alguno de los siguientes elementos al ingresar al país:</p>
            <div className="space-y-4">
              <RadioGroup name="traeAnimales" label="¿Trae productos de origen animal (carne, quesos, lácteos, embutidos, miel)?" onChange={handleChange} />
              <RadioGroup name="traeVegetales" label="¿Trae productos de origen vegetal (frutas frescas, semillas, verduras, tierra, plantas)?" onChange={handleChange} />
              <RadioGroup name="poseeMascotas" label="¿Viaja acompañado de mascotas domésticas (perro, gato)?" onChange={handleChange} />
            </div>
          </div>

          <button type="submit" disabled={isLoading} className="btn-primary w-full py-4 text-base">
            {isLoading ? (
              <span className="flex items-center gap-3">
                <svg className="animate-spin w-5 h-5 text-white" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"/>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"/>
                </svg>
                Procesando Declaración...
              </span>
            ) : (
              <span className="flex items-center gap-2">
                <Sparkles className="w-5 h-5" /> Enviar y Generar Código QR
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
      <div className="absolute top-0 left-0 right-0 h-1.5" style={{ background: 'linear-gradient(90deg, #004481 0%, #0082c9 50%, #d81e05 100%)' }} />

      <div className="max-w-md w-full animate-scale-in">
        <div className="glass p-8 text-center flex flex-col items-center">
          {/* Ícono de éxito */}
          <div className="relative inline-flex mb-5">
            <div className="absolute inset-0 bg-emerald-500/10 blur-xl rounded-full scale-150" />
            <div className="relative icon-wrap icon-emerald">
              <CheckCircle className="w-10 h-10" />
            </div>
          </div>

          <h2 className="text-2xl font-black text-slate-900 mb-1">¡Declaración Generada!</h2>
          <p className="text-slate-500 text-sm mb-6">Su declaración se ha guardado en el sistema nacional.</p>

          {/* Generador de QR Real */}
          <div className="qr-container mb-6 bg-white p-4 rounded-xl border border-slate-200">
            <QRCodeSVG
              value={codigoGenerado}
              size={190}
              level={"H"}
              fgColor="#002d56" /* Azul corporativo oscuro */
              bgColor="#FFFFFF"
              includeMargin={true}
            />
          </div>

          {/* Identificador único */}
          <div className="px-4 py-2 rounded-xl mb-6 font-mono text-sm text-slate-800 bg-slate-100 border border-slate-200 w-full text-center">
            Código Trámite: <span className="font-bold">{codigoGenerado}</span>
          </div>

          {/* Resumen de Datos */}
          {datosDeclaracion && (
            <div className="text-left w-full p-4 rounded-xl mb-6 space-y-2 bg-slate-50 border border-slate-200">
              <p className="text-[10px] text-slate-500 uppercase tracking-widest font-extrabold mb-2">Resumen de Identificación</p>
              <div className="flex justify-between text-sm">
                <span className="text-slate-500 font-medium">Pasajero / RUT</span>
                <span className="text-slate-900 font-mono font-bold">{datosDeclaracion.rut}</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-slate-500 font-medium">Nombre Completo</span>
                <span className="text-slate-900 font-bold">{datosDeclaracion.nombres} {datosDeclaracion.apellidos}</span>
              </div>
              {datosDeclaracion.patente && (
                <div className="flex justify-between text-sm">
                  <span className="text-slate-500 font-medium">Patente Vehículo</span>
                  <span className="text-slate-900 font-mono font-bold">{datosDeclaracion.patente.toUpperCase()}</span>
                </div>
              )}
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
      const rutLimpio = limpiarRut(busqueda);
      
      const pasajeroRes = await fetch(`${API_URL}/api/v1/pasajeros/${rutLimpio}`);
      if (!pasajeroRes.ok) throw new Error('No encontrado');
      const pasajero = await pasajeroRes.json();

      const sagRes = await fetch(`${API_URL}/api/v1/declaraciones-sag/pasajero/${rutLimpio}`);
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
        return { card: 'verify-card-success', icon: 'icon-emerald', iconEl: <CheckCircle className="w-5 h-5" />, badge: 'badge-success' };
      case 'Revisión Requerida':
        return { card: 'verify-card-warning', icon: 'icon-amber', iconEl: <AlertTriangle className="w-5 h-5" />, badge: 'badge-warning' };
      case 'Sin Declaración':
        return { card: 'verify-card-neutral', icon: 'icon-slate', iconEl: <FileText className="w-5 h-5" />, badge: 'badge-neutral' };
      default:
        return { card: 'verify-card-danger', icon: 'icon-red', iconEl: <XCircle className="w-5 h-5" />, badge: 'badge-danger' };
    }
  };

  return (
    <div className="min-h-screen animate-fade-in">
      <div className="absolute top-0 left-0 right-0 h-1.5" style={{ background: 'linear-gradient(90deg, #004481 0%, #0082c9 50%, #d81e05 100%)' }} />

      {/* Header */}
      <header className="sticky top-0 z-50 px-6 py-4 glass" style={{borderRadius: '0 0 20px 20px', borderTop: 'none'}}>
        <div className="max-w-5xl mx-auto flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="icon-wrap icon-indigo">
              <ShieldCheck className="w-5 h-5 text-indigo-800" />
            </div>
            <div>
              <h1 className="font-extrabold text-slate-900 text-base leading-none">SIGFT Oficial</h1>
              <p className="text-xs text-slate-500 mt-1">Inspección de Declaraciones e Ingreso Terrestre</p>
            </div>
          </div>
          <div className="flex items-center gap-4">
            <span className="hidden md:flex items-center gap-1.5 text-xs text-slate-500 font-semibold bg-slate-100 px-3 py-1 rounded-full">
              <Clock className="w-3.5 h-3.5 text-slate-600" /> <LiveClock />
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
          <h2 className="text-xs font-bold text-slate-500 uppercase tracking-widest mb-4 flex items-center gap-2">
            <Search className="w-4 h-4" /> Control Aduanero / Buscar Pasajero
          </h2>
          <form onSubmit={buscarViajero} className="flex flex-col sm:flex-row gap-3">
            <input
              type="text" value={busqueda} required
              onChange={(e) => setBusqueda(e.target.value)}
              placeholder="Ej: 12345678-9 (Sin puntos, con guión)"
              className="input-field flex-1"
            />
            <button type="submit" disabled={cargando} className="btn-primary sm:w-auto">
              {cargando ? 'Buscando...' : 'Consultar Documento'}
            </button>
          </form>
        </div>

        {/* Resultado */}
        {resultado && (
          <div className="grid md:grid-cols-3 gap-6 animate-slide-up">
            {/* Ficha del pasajero */}
            <div className="glass p-6 flex flex-col justify-between">
              <div>
                <h3 className="section-header section-header-indigo">
                  <User className="w-4 h-4" /> Datos de Registro
                </h3>
                <div className="space-y-4 mt-4">
                  <div>
                    <p className="text-xs text-slate-500 mb-0.5">RUT / Identificación</p>
                    <p className="font-bold text-slate-900 text-lg font-mono">{resultado.rut}</p>
                  </div>
                  <div>
                    <p className="text-xs text-slate-500 mb-0.5">Nombre Completo</p>
                    <p className="font-bold text-slate-800">{resultado.nombre}</p>
                  </div>
                  {resultado.patente && (
                    <div>
                      <p className="text-xs text-slate-500 mb-1">Vehículo Asociado</p>
                      <span className="font-mono text-sm font-bold px-3 py-1 rounded-lg text-slate-800 bg-slate-100 border border-slate-200 inline-block">
                        {resultado.patente.toUpperCase()}
                      </span>
                    </div>
                  )}
                </div>
              </div>
              <div className="pt-6 mt-6 border-t border-slate-100">
                <span className="badge badge-success">
                  <CheckCircle className="w-3.5 h-3.5" /> Estado: En Tránsito
                </span>
              </div>
            </div>

            {/* Verificaciones */}
            <div className="md:col-span-2 space-y-3">
              {[
                { label: 'Control Migratorio (PDI)', sub: 'Validación de identidad y libre tránsito', estado: resultado.estadoPDI, icon: <Shield className="w-5 h-5" /> },
                { label: 'Inspección Sanitaria (SAG)', sub: 'Control fitozoosanitario e ingreso de especies', estado: resultado.estadoSAG, icon: <Leaf className="w-5 h-5" /> },
                { label: 'Control Aduanero (Aduanas)', sub: 'Control de mercancías y franquicias', estado: resultado.estadoAduana, icon: <Package className="w-5 h-5" /> },
              ].map(({ label, sub, estado, icon }) => {
                const props = getVerifyProps(estado);
                return (
                  <div key={label} className={`verify-card ${props.card}`}>
                    <div className="flex items-center gap-4">
                      <div className={`icon-wrap ${props.icon}`}>{icon}</div>
                      <div>
                        <h4 className="font-bold text-slate-900 text-sm">{label}</h4>
                        <p className="text-xs text-slate-500 mt-0.5">{sub}</p>
                      </div>
                    </div>
                    <span className={`badge ${props.badge}`}>
                      {props.iconEl}
                      {estado}
                    </span>
                  </div>
                );
              })}

              <div className="pt-4 flex justify-end">
                <button onClick={() => { alert('Cruce Autorizado y Registrado.'); setResultado(null); setBusqueda(''); }} className="btn-emerald">
                  <CheckCircle className="w-5 h-5" /> Registrar y Autorizar Cruce
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Estado vacío */}
        {!resultado && !cargando && (
          <div className="text-center py-16 glass">
            <div className="icon-wrap icon-slate inline-flex mb-4">
              <FileSpreadsheet className="w-8 h-8" />
            </div>
            <p className="text-slate-800 font-bold">Esperando Consulta</p>
            <p className="text-slate-500 text-sm mt-1">Escriba el número de documento de identidad en el buscador superior.</p>
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