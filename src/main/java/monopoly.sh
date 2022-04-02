stty sane
stty size | head -n 1 > /tmp/terminal_size.txt
stty raw && javac game/Monopoly.java && java game/Monopoly
find . -name '*.class' -delete
