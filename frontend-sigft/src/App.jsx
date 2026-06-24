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
  AlertTriangle
} from 'lucide-react';

export default function App() {
  const [vistaActual, setVistaActual] = useState('seleccionar-rol');
  const [datosDeclaracion, setDatosDeclaracion] = useState(null);
  const [codigoGenerado, setCodigoGenerado] = useState('');

  const VistaSeleccionRol = () => (
    <div className="min-h-screen bg-slate-100 flex flex-col items-center justify-center p-4">
      <div className="text-center mb-10">
        <ShieldCheck className="w-20 h-20 text-[#383979] mx-auto mb-4" />
        <h1 className="text-4xl font-bold text-[#383979]">SIGFT</h1>
        <p className="text-[#383979] mt-2">Sistema de Control y Gestión Fronteriza Terrestre</p>
      </div>

      <div className="grid md:grid-cols-2 gap-6 w-full max-w-4xl">
        <button 
          onClick={() => setVistaActual('portal-viajero')}
          className="bg-[#204E73] p-8 rounded-2xl shadow-lg hover:shadow-xl transition-shadow flex flex-col items-center text-center border-2 border-transparent hover:border-blue-400"
        >
          <div className="bg-blue-100 p-4 rounded-full mb-4">
            <User className="w-12 h-12 text-blue-600" />
          </div>
          <h2 className="text-2xl font-bold text-white mb-2">Soy Viajero</h2>
          <p className="text-slate-100">Generar declaración anticipada y obtener mi código QR de cruce fronterizo.</p>
        </button>

        <button 
          onClick={() => setVistaActual('portal-funcionario')}
          className="bg-[#204E73] p-8 rounded-2xl shadow-lg hover:shadow-xl transition-shadow flex flex-col items-center text-center border-2 border-transparent hover:border-emerald-400"
        >
          <div className="bg-emerald-100 p-4 rounded-full mb-4">
            <ShieldCheck className="w-12 h-12 text-emerald-600" />
          </div>
          <h2 className="text-2xl font-bold text-white mb-2">Funcionario / Ventanilla</h2>
          <p className="text-slate-100">Acceso para PDI, SAG y Aduanas. Escaneo de QR y validación de microservicios.</p>
        </button>
      </div>
    </div>
  );

  const VistaPortalViajero = () => {
    const [formData, setFormData] = useState({
      rut: '',
      nombres: '',
      apellidos: '',
      edad: '',
      nacionalidad: '',
      email: '',
      patente: '',
      traeMenores: 'no',
      traeAnimales: 'no',
      traeVegetales: 'no',
      poseeMascotas: 'no'
    });
    const [isLoading, setIsLoading] = useState(false);
    const API_URL = import.meta.env.VITE_API_GATEWAY_URL || 'http://localhost:8080';

    const handleChange = (e) => setFormData({...formData, [e.target.name]: e.target.value});

    const handleSubmit = async (e) => {
      e.preventDefault();
      setIsLoading(true);

      try {
        // 1. Crear Pasajero
        const pasajeroPayload = {
          rut: formData.rut,
          nombre: `${formData.nombres} ${formData.apellidos}`,
          edad: parseInt(formData.edad),
          nacionalidad: formData.nacionalidad,
          email: formData.email
        };

        await fetch(`${API_URL}/api/v1/pasajeros`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(pasajeroPayload)
        });

        // 2. Crear Declaración SAG
        const idDeclaracion = `SAG-${Date.now()}`;
        const sagPayload = {
          idDeclaracion: idDeclaracion,
          fechaRegistro: new Date().toISOString(),
          traeProductosAnimales: formData.traeAnimales === 'si',
          traeProductosVegetales: formData.traeVegetales === 'si',
          poseeMascotas: formData.poseeMascotas === 'si',
          rutPasajero: formData.rut
        };

        await fetch(`${API_URL}/api/v1/declaraciones-sag`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(sagPayload)
        });

        setDatosDeclaracion(formData);
        setCodigoGenerado(idDeclaracion);
        setVistaActual('qr-generado');
      } catch (error) {
        console.error("Error al registrar:", error);
        alert("Ocurrió un error de conexión con los microservicios.");
      } finally {
        setIsLoading(false);
      }
    };

    return (
      <div className="min-h-screen bg-slate-50 p-4 md:p-8">
        <button onClick={() => setVistaActual('seleccionar-rol')} className="text-blue-600 font-medium mb-6 flex items-center">
           Volver al inicio
        </button>
        <div className="max-w-2xl mx-auto bg-white rounded-2xl shadow p-6 md:p-8">
          <div className="flex items-center gap-3 mb-6 border-b pb-4">
            <FileText className="text-blue-600 w-8 h-8" />
            <h2 className="text-2xl font-bold text-slate-800">Declaración de Cruce Fronterizo</h2>
          </div>
          
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">RUT o Pasaporte</label>
                <input required type="text" name="rut" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" placeholder="12.345.678-9" />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Patente Vehículo (Opcional)</label>
                <input type="text" name="patente" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" placeholder="ABCD-12" />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Nombres</label>
                <input required type="text" name="nombres" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Apellidos</label>
                <input required type="text" name="apellidos" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Edad</label>
                <input required type="number" name="edad" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1">Nacionalidad</label>
                <input required type="text" name="nacionalidad" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" placeholder="Chilena" />
              </div>
              <div className="md:col-span-2">
                <label className="block text-sm font-medium text-slate-700 mb-1">Email</label>
                <input required type="email" name="email" onChange={handleChange} className="w-full p-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none" placeholder="correo@ejemplo.com" />
              </div>
            </div>

            <div className="p-4 bg-slate-50 rounded-lg border border-slate-200">
              <label className="block text-sm font-medium text-slate-800 mb-2">¿Viaja con menores de edad?</label>
              <div className="flex gap-4">
                <label className="flex items-center gap-2"><input type="radio" name="traeMenores" value="si" onChange={handleChange} /> Sí</label>
                <label className="flex items-center gap-2"><input defaultChecked type="radio" name="traeMenores" value="no" onChange={handleChange} /> No</label>
              </div>
            </div>

            <div className="p-4 bg-orange-50 rounded-lg border border-orange-200 space-y-4">
              <h3 className="font-bold text-slate-800">Declaración Jurada SAG</h3>
              
              <div>
                <label className="block text-sm font-medium text-slate-800 mb-2">¿Trae productos de origen animal?</label>
                <div className="flex gap-4">
                  <label className="flex items-center gap-2"><input type="radio" name="traeAnimales" value="si" onChange={handleChange} /> Sí</label>
                  <label className="flex items-center gap-2"><input defaultChecked type="radio" name="traeAnimales" value="no" onChange={handleChange} /> No</label>
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-slate-800 mb-2">¿Trae productos de origen vegetal?</label>
                <div className="flex gap-4">
                  <label className="flex items-center gap-2"><input type="radio" name="traeVegetales" value="si" onChange={handleChange} /> Sí</label>
                  <label className="flex items-center gap-2"><input defaultChecked type="radio" name="traeVegetales" value="no" onChange={handleChange} /> No</label>
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-slate-800 mb-2">¿Viaja con mascotas (perros, gatos, hurones, aves)?</label>
                <div className="flex gap-4">
                  <label className="flex items-center gap-2"><input type="radio" name="poseeMascotas" value="si" onChange={handleChange} /> Sí</label>
                  <label className="flex items-center gap-2"><input defaultChecked type="radio" name="poseeMascotas" value="no" onChange={handleChange} /> No</label>
                </div>
              </div>
            </div>

            <button disabled={isLoading} type="submit" className="w-full bg-blue-600 text-white font-bold py-3 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50">
              {isLoading ? 'Conectando con Microservicios...' : 'Generar Código QR'}
            </button>
          </form>
        </div>
      </div>
    );
  };

  const VistaQR = () => (
    <div className="min-h-screen bg-slate-50 flex flex-col items-center justify-center p-4">
      <div className="bg-white p-8 rounded-2xl shadow-lg max-w-md w-full text-center">
        <CheckCircle className="w-16 h-16 text-emerald-500 mx-auto mb-4" />
        <h2 className="text-2xl font-bold text-slate-800 mb-2">¡Declaración Exitosa!</h2>
        <p className="text-slate-600 mb-6">Presente este código en la ventanilla única fronteriza.</p>
        
        <div className="bg-slate-100 p-8 rounded-xl flex items-center justify-center mb-6 border-2 border-dashed border-slate-300">
          <QrCode className="w-32 h-32 text-slate-800" />
        </div>
        <p className="text-sm font-mono bg-slate-200 py-2 rounded mb-6 text-slate-700">ID: {codigoGenerado}</p>

        <button onClick={() => setVistaActual('seleccionar-rol')} className="text-blue-600 font-medium hover:underline">
          Finalizar y volver al inicio
        </button>
      </div>
    </div>
  );

  const VistaFuncionario = () => {
    const [busqueda, setBusqueda] = useState('');
    const [resultado, setResultado] = useState(null);
    const [cargando, setCargando] = useState(false);

    const API_URL = import.meta.env.VITE_API_GATEWAY_URL || 'http://localhost:8080';

    const buscarViajero = async (e) => {
      e.preventDefault();
      setCargando(true);
      setResultado(null);
      
      try {
        // 1. Obtener pasajero
        const pasajeroRes = await fetch(`${API_URL}/api/v1/pasajeros/${busqueda}`);
        if (!pasajeroRes.ok) throw new Error('Pasajero no encontrado');
        const pasajero = await pasajeroRes.json();

        // 2. Obtener estado SAG
        const sagRes = await fetch(`${API_URL}/api/v1/declaraciones-sag/pasajero/${busqueda}`);
        let estadoSAG = 'Sin Declaración';
        if (sagRes.ok) {
          const declaraciones = await sagRes.json();
          if (declaraciones.length > 0) {
            estadoSAG = declaraciones[0].requiereRevisionSAG ? 'Revisión Requerida' : 'Aprobado';
          }
        }

        setResultado({
          rutPasaporte: pasajero.rut,
          nombres: pasajero.nombre,
          apellidos: '',
          patente: pasajero.vehiculos?.length > 0 ? pasajero.vehiculos[0].patente : null,
          traeMenores: 'no', // Se asume no para la demo actual
          estadoPDI: 'Aprobado',
          estadoSAG: estadoSAG,
          estadoAduana: 'Aprobado'
        });
      } catch (error) {
        console.error("Error al buscar viajero:", error);
        alert("No se encontró información para el RUT ingresado en el sistema.");
      } finally {
        setCargando(false);
      }
    };

    return (
      <div className="min-h-screen bg-slate-100">
        <header className="bg-slate-900 text-white p-4 shadow-md flex justify-between items-center">
          <div className="flex items-center gap-2">
            <ShieldCheck className="w-6 h-6 text-emerald-400" />
            <h1 className="font-bold text-xl">SIGFT Ventanilla Única</h1>
          </div>
          <button onClick={() => setVistaActual('seleccionar-rol')} className="flex items-center gap-2 text-slate-300 hover:text-white">
            <LogOut className="w-4 h-4" /> Salir
          </button>
        </header>

        <main className="p-4 md:p-8 max-w-5xl mx-auto">
          <div className="bg-white rounded-xl shadow p-6 mb-6 border-t-4 border-emerald-500">
            <h2 className="text-lg font-bold text-slate-800 mb-4">Escanear QR o Ingresar Documento</h2>
            <form onSubmit={buscarViajero} className="flex gap-4">
              <div className="relative flex-1">
                <Search className="absolute left-3 top-3 text-slate-400 w-5 h-5" />
                <input 
                  type="text" 
                  value={busqueda}
                  onChange={(e) => setBusqueda(e.target.value)}
                  placeholder="Ingrese el código SIGFT o RUT..."
                  className="w-full pl-10 p-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-emerald-500 outline-none"
                  required
                />
              </div>
              <button type="submit" className="bg-emerald-600 hover:bg-emerald-700 text-white font-bold px-6 py-3 rounded-lg flex items-center gap-2 transition-colors">
                <QrCode className="w-5 h-5" /> Consultar
              </button>
            </form>
          </div>

          {cargando ? (
            <div className="text-center py-10 text-slate-500 animate-pulse">Consultando bases de datos de PDI, SAG y Aduanas...</div>
          ) : resultado && (
            <div className="grid md:grid-cols-3 gap-6">
              
              <div className="md:col-span-1 bg-white rounded-xl shadow p-6">
                <h3 className="font-bold text-slate-800 mb-4 border-b pb-2">Datos del Viajero</h3>
                <div className="space-y-3 text-sm">
                  <p><span className="text-slate-500 block">RUT / Pasaporte</span> <span className="font-semibold text-lg">{resultado.rutPasaporte || busqueda}</span></p>
                  <p><span className="text-slate-500 block">Nombre Completo</span> <span className="font-medium">{resultado.nombres} {resultado.apellidos}</span></p>
                  {resultado.patente && <p><span className="text-slate-500 block">Vehículo</span> <span className="font-medium bg-slate-100 px-2 py-1 rounded border inline-block mt-1">{resultado.patente}</span></p>}
                  {resultado.traeMenores === 'si' && <p className="text-orange-600 font-medium flex items-center gap-1 mt-2"><AlertTriangle className="w-4 h-4"/> Viaja con menores</p>}
                </div>
              </div>

              <div className="md:col-span-2 space-y-4">
                <div className={`p-4 rounded-xl border flex items-center justify-between ${resultado.estadoPDI === 'Aprobado' ? 'bg-green-50 border-green-200' : 'bg-red-50 border-red-200'}`}>
                  <div className="flex items-center gap-3">
                    <div className={`p-2 rounded-full ${resultado.estadoPDI === 'Aprobado' ? 'bg-green-100 text-green-600' : 'bg-red-100 text-red-600'}`}>
                      {resultado.estadoPDI === 'Aprobado' ? <CheckCircle className="w-6 h-6"/> : <XCircle className="w-6 h-6"/>}
                    </div>
                    <div>
                      <h4 className="font-bold text-slate-800">Policía de Investigaciones (PDI)</h4>
                      <p className="text-sm text-slate-600">Validación de arraigo y antecedentes</p>
                    </div>
                  </div>
                  <span className={`font-bold px-3 py-1 rounded-full text-sm ${resultado.estadoPDI === 'Aprobado' ? 'bg-green-200 text-green-800' : 'bg-red-200 text-red-800'}`}>
                    {resultado.estadoPDI}
                  </span>
                </div>

                <div className={`p-4 rounded-xl border flex items-center justify-between ${resultado.estadoSAG === 'Aprobado' ? 'bg-green-50 border-green-200' : 'bg-orange-50 border-orange-200'}`}>
                  <div className="flex items-center gap-3">
                    <div className={`p-2 rounded-full ${resultado.estadoSAG === 'Aprobado' ? 'bg-green-100 text-green-600' : 'bg-orange-100 text-orange-600'}`}>
                      {resultado.estadoSAG === 'Aprobado' ? <CheckCircle className="w-6 h-6"/> : <AlertTriangle className="w-6 h-6"/>}
                    </div>
                    <div>
                      <h4 className="font-bold text-slate-800">Servicio Agrícola y Ganadero (SAG)</h4>
                      <p className="text-sm text-slate-600">Control fito/zoosanitario</p>
                    </div>
                  </div>
                  <span className={`font-bold px-3 py-1 rounded-full text-sm ${resultado.estadoSAG === 'Aprobado' ? 'bg-green-200 text-green-800' : 'bg-orange-200 text-orange-800'}`}>
                    {resultado.estadoSAG}
                  </span>
                </div>

                <div className="p-4 rounded-xl border bg-green-50 border-green-200 flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <div className="p-2 rounded-full bg-green-100 text-green-600">
                       <CheckCircle className="w-6 h-6"/>
                    </div>
                    <div>
                      <h4 className="font-bold text-slate-800">Aduanas de Chile</h4>
                      <p className="text-sm text-slate-600">Control de franquicias y mercancías</p>
                    </div>
                  </div>
                  <span className="font-bold px-3 py-1 rounded-full text-sm bg-green-200 text-green-800">
                    Aprobado
                  </span>
                </div>

                <div className="mt-6 flex justify-end">
                  <button className="bg-slate-800 text-white font-bold px-8 py-3 rounded-lg hover:bg-slate-900 shadow-lg">
                    Registrar Cruce Fronterizo
                  </button>
                </div>
              </div>

            </div>
          )}
        </main>
      </div>
    );
  };

  switch(vistaActual) {
    case 'portal-viajero': return <VistaPortalViajero />;
    case 'qr-generado': return <VistaQR />;
    case 'portal-funcionario': return <VistaFuncionario />;
    default: return <VistaSeleccionRol />;
  }
}