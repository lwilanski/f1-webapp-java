import { hideForm } from "./utils.js";

export async function showDrivers() {
  const res = await fetch("/api/drivers");
  const drivers = await res.json();

  let html = `<h2>Drivers</h2><table><tr>
      <th>Full name</th><th>Nationality</th><th>Number</th></tr>`;
  drivers.forEach(d => {
    html += `<tr><td>${d.name + ' ' + d.surname}</td>
                 <td>${d.nationality}</td><td>${d.number}</td></tr>`;
  });
  html += `</table>`;
  hideForm();
  document.getElementById("result").innerHTML = html;
}
