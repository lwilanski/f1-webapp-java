export function timeToSeconds(str) {
    if (!str) return null;
    const [m, s] = str.split(":");
    return +m * 60 + +s;
  }
  
  export function secondsToTimeString(sec) {
    const m = Math.floor(sec / 60);
    const s = (sec % 60).toFixed(3).padStart(6, "0");
    return `${m}:${s}`;
  }
  
  export function hideForm() {
    document.getElementById("formContainer").style.display = "none";
  }  