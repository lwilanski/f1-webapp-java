import { hideForm } from "./utils.js";
import { showDrivers } from "./drivers.js";
import { showTeams } from "./teams.js";
import { showLatestQualy } from "./qualy.js";
import { showLatestRace } from "./race.js";
import { showRaceForm, searchRace } from "./findRace.js";

document.getElementById("driversBtn").onclick = showDrivers;
document.getElementById("teamsBtn").onclick = showTeams;
document.getElementById("qualyBtn").onclick = showLatestQualy;
document.getElementById("raceBtn").onclick = showLatestRace;

document.getElementById("findRaceBtn").onclick = showRaceForm;
document.getElementById("searchBtn").onclick = () => {
  const y = document.getElementById("yearInput").value;
  const r = document.getElementById("roundInput").value;
  if (y && r) searchRace(y, r); else alert("Enter year and round");
};
