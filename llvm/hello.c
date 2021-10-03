#include <stdio.h>

/*

http://llvm.org/docs/GettingStartedVS.html

set PATH=C:\Program Files\LLVM\bin;%PATH%
set PATH=D:\logiciel\llvm12\build\Debug\bin;%PATH%

clang -c hello.c -emit-llvm -o hello.bc

Pour avoir le code llvm (fichier hello.ll):
clang hello.c -S -emit-llvm

Pour avoir le code assembleur (fichier hello.s):
clang hello.c -S

Pour voir le code LLVM :
llvm-dis < hello.bc | more

Pour compiler avec mvc :
set PATH=C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\bin\Hostx64\x64;%PATH%
llc -filetype=obj hello.bc
link hello.obj -defaultlib:libcmt

set path=C:\tmp6\llvm\build\llvm\bin\Debug;%PATH%

https://releases.llvm.org/12.0.1/docs/GettingStartedVS.html
https://llvm.org/docs/GettingStartedVS.html

*/

int main() {
  printf("hello world\n");
  return 0;
}