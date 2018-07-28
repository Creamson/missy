pandoc -s --number-sections --table-of-contents --standalone --filter=pandoc-citeproc --bibliography=bibliography.bib -o ../build/MultiwinnerVoting.pdf header.yaml MultiwinnerVoting.md
