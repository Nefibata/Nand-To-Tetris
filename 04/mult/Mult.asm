@R1
D=M
@LOAD
D,JEQ
@R0
D=M
@LOAD
D,JEQ
@temp
M=D
@temp
M=0
@i
M=0

(LOOP)
	@R1
	D=M
	@i
	D=D-M
	@LOAD
	D;JEQ
	@R0
	D=M
	@temp
	M=M+D
	@i
	M=M+1
	@LOOP
	0;JMP
	
(LOAD)
@temp
D=M
@R2
M=D
@temp
M=0

(END)
@END
0;JMP
	
