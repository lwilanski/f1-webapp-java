import { hideForm } from "./utils.js";

export async function showLatestRace() {
  const { date, raceName, circuitName, city, results } =
        await (await fetch("/api/race/latest")).json();

  results.sort((a, b) => {
    const posA = a.position === 0 ? Infinity : a.position;
    const posB = b.position === 0 ? Infinity : b.position;
    return posA - posB;
  });

  let html = `<h2>${raceName}</h2>
      <p><b>Date:</b> ${date}</p>
      <p><b>Circuit:</b> ${circuitName} (${city})</p>
      <table><thead><tr>
        <th>Pos</th><th>Driver</th><th>Team</th>
        <th>Grid</th><th>Time</th><th>Points</th>
      </tr></thead><tbody>`;

  results.forEach(r => {
    const isDnf   = r.position === 0;
    const style   = !isDnf && r.position === 1 ? ' style="background:#FFD700"'
                  : !isDnf && r.position === 2 ? ' style="background:#C0C0C0"'
                  : !isDnf && r.position === 3 ? ' style="background:#CD7F32"' : "";

    const driver  = r.grid === 1 && !isDnf
                  ? `<b>${r.driverName} 🏁</b>` : r.driverName;

    const posCell = isDnf ? "—" : r.position;

    html += `<tr${style}>
        <td>${posCell}</td>
        <td>${driver}</td>
        <td>${r.teamName}</td>
        <td>${r.grid}</td>
        <td>${r.time ?? "—"}</td>
        <td>${r.points}</td>
      </tr>`;
  });

  html += `</tbody></table>`;
  hideForm();
  document.getElementById("result").innerHTML = html;
}
