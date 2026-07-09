const data = {
  rut: "21896998-4",
  nombre: "Lucas Guerra",
  edad: 20,
  nacionalidad: "Chilena",
  email: "lucasguerra@gmail.com"
};

fetch('http://localhost:8080/api/v1/pasajeros', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(data)
})
.then(res => {
  console.log('Status:', res.status);
  return res.json();
})
.then(json => {
  console.log('Response:', json);
})
.catch(err => {
  console.error('Error:', err);
});
