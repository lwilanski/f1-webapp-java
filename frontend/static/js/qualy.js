import { hideForm } from "./utils.js";

export async function showLatestQualy() {
  const { date, raceName, circuitName, city, results } =
        await (await fetch("/api/qualy/latest")).json();

  results.sort((a, b) => a.gridPosition - b.gridPosition);

  let html = `<h2>${raceName}</h2>
        <p><b>Date:</b> ${date}</p>
        <p><b>Circuit:</b> ${circuitName} (${city})</p>
        <table><thead><tr>
          <th>Driver</th><th>Team</th><th>Q1</th><th>Q2</th><th>Q3</th><th>Grid</th>
        </tr></thead><tbody>`;
  results.forEach(r => {
    html += `<tr><td>${r.driverName}</td><td>${r.teamName}</td>
        <td>${r.q1 ?? "—"}</td><td>${r.q2 ?? "—"}</td>
        <td>${r.q3 ?? "—"}</td><td>${r.gridPosition}</td></tr>`;
  });
  html += `</tbody></table>`;
  hideForm();
  document.getElementById("result").innerHTML = html;
}
