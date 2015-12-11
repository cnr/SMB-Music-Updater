
---

Conversion steps

- [ ] GUI with one start button
  - [ ] Start button disappears to turn into progress bar when clicked
- [ ] Identify location of SMB on the machine
  - [x] OS X
  - [ ] Windows
- [x] Unpack gameaudio.dat into temporary directory
  - [ ] Progress bar
- [x] Acquire/convert new music (see below)
- [x] Replace files into unpacked data
  - [ ] Progress bar
- [x] Repack gameaudio.dat
  - [ ] Progress bar
- [x] Replace gameaudio.dat

---

Acquiring/converting PS4 SMB music

- [ ] Download ffmpeg executable if not installed
  - [ ] OS X
  - [ ] Windows
  - [ ] Linux
- [x] Download songs from bandcamp (hardcode individual song permalinks to use with youtube-dl)
  - [x] Mapping of original names -> SMB names for download filenames
- [ ] Convert downloaded songs to adpcm_ms with ffmpeg
  - [x] OS X
  - [ ] Windows
- [x] Trim audio files with ffmpeg
  - [ ] Map of loopable portions

---
