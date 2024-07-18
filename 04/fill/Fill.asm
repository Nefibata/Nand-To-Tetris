// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen
// by writing 'black' in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen by writing
// 'white' in every pixel;
// the screen should remain fully clear as long as no key is pressed.
(J3)
(LOOP1)
	@KBD
	D=M
	@J1
	D;JNE 
	@J2
	D;JEQ
	@LOOP1
	0;JMP
	
(J1)
@SCREEN
D=A 
@address
M=D
@8192
D=A
@i
M=D
(LOOP2)
	@address
	A=M
	M=-1
	@address
	M=M+1
	@i
	M=M-1
	D=M
	@J3
	D;JEQ
	@LOOP2
	0;JMP
	
(J2)
@SCREEN
D=A 
@address
M=D
@8192
D=A
@i
M=D
(LOOP3)
	@address
	A=M
	M=0
	@address
	M=M+1
	@i
	M=M-1
	D=M
	@J3
	D;JEQ
	@LOOP3
	0;JMP

