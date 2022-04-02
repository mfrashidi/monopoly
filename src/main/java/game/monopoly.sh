stty sane
stty size | head -n 1 > /tmp/terminal_size.txt
stty raw && javac Monopoly.java && java Monopoly
find . -name '*.class' -delete
