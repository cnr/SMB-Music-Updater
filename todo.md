
---

Conversion steps

- [ ] GUI with one start button
  - [ ] Start button disappears to turn into progress bar when clicked
- [ ] Identify location of SMB on the machine
  - [ ] OS X
  - [ ] Windows
  - [ ] Linux
- [ ] Unpack gameaudio.dat into temporary directory
  - [ ] Progress bar
- [ ] Acquire/convert new music (see below)
- [ ] Replace files into unpacked data
  - [ ] Progress bar
- [ ] Repack gameaudio.dat
  - [ ] Progress bar
- [ ] Replace gameaudio.dat

---

Acquiring/converting PS4 SMB music

- [ ] Download youtube-dl executable if not installed
  - [ ] OS X
  - [ ] Windows
  - [ ] Linux
- [ ] Download ffmpeg executable if not installed
  - [ ] OS X
  - [ ] Windows
  - [ ] Linux
- [ ] Download songs from bandcamp (hardcode individual song permalinks to use with youtube-dl)
  - [ ] Mapping of original names -> SMB names for download filenames
- [ ] Convert downloaded songs to adpcm_ms with ffmpeg
- [ ] Trim audio files with ffmpeg
  - [ ] Map of loopable portions

---
