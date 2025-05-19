import { hideForm } from "./utils.js";

export function showRaceForm() {
  document.getElementById("result").innerHTML = "";
  document.getElementById("formContainer").style.display = "block";
}

export async function searchRace(year, round) {
  const res = await fetch(`/api/race/${year}/${round}`);
  if (!res.ok) {
    document.getElementById("result").innerHTML =
      "<p>Error fetching race.</p>";
    return;
  }

  const { races: race } = await res.json();
  const results = race.results;

  results.sort((a, b) => {
    const posA = a.position === 0 ? Infinity : a.position;
    const posB = b.position === 0 ? Infinity : b.position;
    return posA - posB;
  });

  let html = `<h2>${race.raceName}</h2>
      <p><b>Date:</b> ${race.date}</p>
      <p><b>Circuit:</b> ${race.circuit.circuitName} (${race.circuit.city})</p>
      <table><thead><tr>
        <th>Pos</th><th>Driver</th><th>Team</th>
        <th>Grid</th><th>Time</th><th>Points</th>
      </tr></thead><tbody>`;

  results.forEach(r => {
    const isDnf = r.position === 0;

    const style = !isDnf && r.position === 1
        ? ' style="background:#FFD700"'
        : !isDnf && r.position === 2
        ? ' style="background:#C0C0C0"'
        : !isDnf && r.position === 3
        ? ' style="background:#CD7F32"'
        : "";

    const driver = r.grid === 1 && !isDnf
        ? `<b>${r.driver.name} ${r.driver.surname} 🏁</b>`
        : `${r.driver.name} ${r.driver.surname}`;

    const posCell = isDnf ? "—" : r.position;

    html += `<tr${style}>
        <td>${posCell}</td>
        <td>${driver}</td>
        <td>${r.team.teamName}</td>
        <td>${r.grid}</td>
        <td>${r.time ?? "—"}</td>
        <td>${r.points}</td>
      </tr>`;
  });

  html += `</tbody></table>`;
  hideForm();
  document.getElementById("result").innerHTML = html;
}
