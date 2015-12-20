
Super Meat Boy .dat file format
-------------------------------

Let `int` be defined as a little-endian dword  
Let `long` be defined as a little-endian qword

After the file header, each file's contents are written sequentially into the .dat file.

---

### Header

| Data                     | Size        |
| ------------------------ | ----------- |
| Directory count (total)  | `long`      |
| Directory metadata       | (see below) |
| File count (total)       | `long`      |
| File metadata            | (see below) |
| Length(Directory string) | (see below) |
| Length(File string)      | (see below) |
| Directory string         | (see below) |
| File string              | (see below) |


---

#### Directory metadata

For each directory:

| Data                                             | Size  |
| ------------------------------------------------ | ----- |
| Current directory number (0-based index)         | `int` |
| Current file count - 1 (minimum 0)               | `int` |

The current file count is determined at the time a directory is found.

For example:

- root
  - folder1 `count: 0 => 0`
    - file1
  - folder2 `count: 1 => 0`
    - file2
    - file3
  - file4
  - folder3 `count: 4 => 3`
    - file5
    - file6
  - folder4 `count: 5 => 4`
    - file7
  - file8

The main implementation traverses directories and files depth-first in alphabetical order, case-insensitive.


---

#### File metadata

For each file:

| Data                                                 | Size  |
| ---------------------------------------------------- | ----- |
| Offset of file contents, starting from the beginning | `int` |
| File size in bytes                                   | `int` |
| Directory ID                                         | `int` |

The header size must be included in the offset calculation.

For example:

- file0 `offset: <header size>`
- file1 `offset: <header size> + <file0 size>`

The directory ID is the 0-based index of the directory containing this file.

For example:

- root `id: 0`
  - folder2 `id: 1`
    - file2 `id: 1`
    - file3 `id: 1`
  - file4 `id: 0`

---

#### Directory and file strings

The list of directory and file names are joined with a `NUL` delimiter. You may (must?) include a trailing `NUL` byte at the end.
