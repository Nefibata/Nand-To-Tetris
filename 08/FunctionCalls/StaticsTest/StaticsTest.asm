(Sys.init)
D=0
@6
D=A+D
@R0
M=M+1
A=M-1
M=D
D=0
@8
D=A+D
@R0
M=M+1
A=M-1
M=D
@Sys.init1
D=A
@R0
M=M+1
A=M-1
M=D
@R1
D=M
@R0
M=M+1
A=M-1
M=D
@R2
D=M
@R0
M=M+1
A=M-1
M=D
@R3
D=M
@R0
M=M+1
A=M-1
M=D
@R4
D=M
@R0
M=M+1
A=M-1
M=D
@0
D=M
D=D-1
D=D-1
D=D-1
D=D-1
D=D-1
@2
D=D-A
@R2
M=D
@R0
D=M
@R1
M=D
@Class1.set
0;JMP
(Sys.init1)
@5
D=A
@R0
A=M
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M+1
A=M
M=D
D=0
@23
D=A+D
@R0
M=M+1
A=M-1
M=D
D=0
@15
D=A+D
@R0
M=M+1
A=M-1
M=D
@Sys.init2
D=A
@R0
M=M+1
A=M-1
M=D
@R1
D=M
@R0
M=M+1
A=M-1
M=D
@R2
D=M
@R0
M=M+1
A=M-1
M=D
@R3
D=M
@R0
M=M+1
A=M-1
M=D
@R4
D=M
@R0
M=M+1
A=M-1
M=D
@0
D=M
D=D-1
D=D-1
D=D-1
D=D-1
D=D-1
@2
D=D-A
@R2
M=D
@R0
D=M
@R1
M=D
@Class2.set
0;JMP
(Sys.init2)
@5
D=A
@R0
A=M
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M+1
A=M
M=D
@Sys.init3
D=A
@R0
M=M+1
A=M-1
M=D
@R1
D=M
@R0
M=M+1
A=M-1
M=D
@R2
D=M
@R0
M=M+1
A=M-1
M=D
@R3
D=M
@R0
M=M+1
A=M-1
M=D
@R4
D=M
@R0
M=M+1
A=M-1
M=D
@0
D=M
D=D-1
D=D-1
D=D-1
D=D-1
D=D-1
@0
D=D-A
@R2
M=D
@R0
D=M
@R1
M=D
@Class1.get
0;JMP
(Sys.init3)
@Sys.init4
D=A
@R0
M=M+1
A=M-1
M=D
@R1
D=M
@R0
M=M+1
A=M-1
M=D
@R2
D=M
@R0
M=M+1
A=M-1
M=D
@R3
D=M
@R0
M=M+1
A=M-1
M=D
@R4
D=M
@R0
M=M+1
A=M-1
M=D
@0
D=M
D=D-1
D=D-1
D=D-1
D=D-1
D=D-1
@0
D=D-A
@R2
M=D
@R0
D=M
@R1
M=D
@Class2.get
0;JMP
(Sys.init4)
(END)
@END
0;JMP
(Class1.set)
@R2
D=M
@0
D=A+D
A=D
D=M
@R0
M=M+1
A=M-1
M=D
@16
D=A
@R0
A=M
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M+1
A=M
M=D
@R2
D=M
@1
D=A+D
A=D
D=M
@R0
M=M+1
A=M-1
M=D
@17
D=A
@R0
A=M
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M+1
A=M
M=D
D=0
@0
D=A+D
@R0
M=M+1
A=M-1
M=D
@R0
A=M-1
D=M
@R2
A=M
M=D
@R2
D=M
@R0
M=D+1
@R1
M=M-1
A=M
D=M
@R4
M=D
@R1
M=M-1
A=M
D=M
@R3
M=D
@R1
M=M-1
A=M
D=M
@R2
M=D
@R1
M=M-1
A=M-1
D=M
@R0
A=M
M=D
@R1
A=M
D=M
@R1
M=D
@R0
A=M
A=M
0;JMP
(Class1.get)
@16
D=A
D=M
@R0
M=M+1
A=M-1
M=D
@17
D=A
D=M
@R0
M=M+1
A=M-1
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M-1
M=M-D
@R0
A=M-1
D=M
@R2
A=M
M=D
@R2
D=M
@R0
M=D+1
@R1
M=M-1
A=M
D=M
@R4
M=D
@R1
M=M-1
A=M
D=M
@R3
M=D
@R1
M=M-1
A=M
D=M
@R2
M=D
@R1
M=M-1
A=M-1
D=M
@R0
A=M
M=D
@R1
A=M
D=M
@R1
M=D
@R0
A=M
A=M
0;JMP
(Class2.set)
@R2
D=M
@0
D=A+D
A=D
D=M
@R0
M=M+1
A=M-1
M=D
@16
D=A
@R0
A=M
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M+1
A=M
M=D
@R2
D=M
@1
D=A+D
A=D
D=M
@R0
M=M+1
A=M-1
M=D
@17
D=A
@R0
A=M
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M+1
A=M
M=D
D=0
@0
D=A+D
@R0
M=M+1
A=M-1
M=D
@R0
A=M-1
D=M
@R2
A=M
M=D
@R2
D=M
@R0
M=D+1
@R1
M=M-1
A=M
D=M
@R4
M=D
@R1
M=M-1
A=M
D=M
@R3
M=D
@R1
M=M-1
A=M
D=M
@R2
M=D
@R1
M=M-1
A=M-1
D=M
@R0
A=M
M=D
@R1
A=M
D=M
@R1
M=D
@R0
A=M
A=M
0;JMP
(Class2.get)
@16
D=A
D=M
@R0
M=M+1
A=M-1
M=D
@17
D=A
D=M
@R0
M=M+1
A=M-1
M=D
@R0
M=M-1
A=M
D=M
@R0
A=M-1
M=M-D
@R0
A=M-1
D=M
@R2
A=M
M=D
@R2
D=M
@R0
M=D+1
@R1
M=M-1
A=M
D=M
@R4
M=D
@R1
M=M-1
A=M
D=M
@R3
M=D
@R1
M=M-1
A=M
D=M
@R2
M=D
@R1
M=M-1
A=M-1
D=M
@R0
A=M
M=D
@R1
A=M
D=M
@R1
M=D
@R0
A=M
A=M
0;JMP
