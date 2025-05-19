import { hideForm } from "./utils.js";

export async function showTeams() {
  const teams = await (await fetch("/api/teams")).json();

  let html = `<h2>Teams</h2><table><tr>
      <th>Name</th><th>Nationality</th><th>First appearance</th>
      <th>Constructors titles</th><th>Drivers titles</th></tr>`;
  teams.forEach(t => {
    html += `<tr><td>${t.teamName}</td><td>${t.teamNationality}</td>
        <td>${t.firstAppeareance ?? "—"}</td>
        <td>${t.constructorsChampionships ?? "—"}</td>
        <td>${t.driversChampionships ?? "—"}</td></tr>`;
  });
  html += `</table>`;
  hideForm();
  document.getElementById("result").innerHTML = html;
}
