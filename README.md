# TechLabs
## Лабораторная работа 1
### Point 1
```cpp
#include "pch.h"
#include "framework.h"

BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
                     )
{
    switch (ul_reason_for_call)
    {
    case DLL_PROCESS_ATTACH:
    case DLL_THREAD_ATTACH:
    case DLL_THREAD_DETACH:
    case DLL_PROCESS_DETACH:
        break;
    }
    return TRUE;
}

#include <iostream>

extern "C"
{
    __declspec (dllexport) void __stdcall Java_MyClass_MyMethod()
    {
        std::cout << "Hello Java from C++";
    }

    __declspec (dllexport) void MyMethod()
    {
        std::cout << "Hello C# from C++";
    }
}
```
Для начала, нужно создать срр файл и подключить dll библиотеку, а также перевести конфигурацию с Debug на Release. В конце исходника нужно написать extern "C", а внутри методы, которые мы хотим чтоб вызывались в Java и C#. "__declspec (dllexport)" - требование для каждого dll метода. Без этой приписки работать не будет. "__stdcall" - требование для Java, чтобы вызвать метод из dll. Лично у меня метод называется с припиской Java_"имя класса". В источнике, с которого я брал информацию, было сказано, что это тоже требование для вызова в Java, однако я знаю людей, у которых работало и без этого. В C# приписка "__stdcall" необязательна. 

#### Для работы с Java:
Нужно сбилдить проект и скопировать dll файл (не наш cpp файл, а именно dll) в проект в IDE, но только не внутрь src или каких-то других папок. Код в Java выглядит так:
```java
public class MyClass {
    public native void MyMethod();

    static
    {
        System.loadLibrary("Dll1");
    }
    public static void main(String args[]) {
        new MyClass().MyMethod();
    }
}
```
Мы объявляем метод срр файла с модификатором native как раз для того, чтобы вызывать его из dll. Через System.loadLibrary("имя dll") загружаем библиотеку и в main вызываем метод.

#### Для работы с C#:
Нужно сбилдить проект и скопировать путь на dll файл, затем импортировать dll: в DllImport указать этот путь и указать CallingConvention = Cdecl, иначе выкинется ошибка. У метода ставится модификатор extern (как в Java native). Код выглядит так:
```cs
using System;
using System.Runtime.InteropServices;

namespace CPPInterop
{
    internal static class Program
    {
        private const string _dllPath = @"C:\Users\volok\source\repos\InteropCPP\x64\Release\Dll1";

        [DllImport(_dllPath, CallingConvention = CallingConvention.Cdecl)]

        public static extern void MyMethod();
        
        private static void Main()
        {
            MyMethod();
        }
    }
}
```

Из сложностей работы с интеропом особо ничего не могу выделить - у меня проблем не было. Ограничений работы я тоже не увидел.
Источники информации:
https://www.youtube.com/watch?v=o-ass4mkdiA&t=0s
https://www.youtube.com/watch?v=41leCIAzSd0&t=0s

### Point 2
#### F# и декомпиляция в C#:
```fs
let multi x y = x * y
let plus x y = x + y
type IntOrBool =
    | I of int
    | B of bool
let result x y z = multi x y |> plus z
let unionTrial x =
    let i = I x
    let b = B (x % 2 = 0)
    printfn $"%d{x}
    printfn $"{i} is odd?"
    printfn $"{b}!"
```
Честно, разбирался с Computation Expressions, но мало что понял, поэтому код очень простой и написан с использованием Pipe Operator и Discriminated Union. Его вызов в C#:
```cs
using System;

namespace CSharpTesting
{
    internal static class Program
    {
        private static void Main()
        {
            var res = global::Program.result(1, 2, 3);
            global::Program.unionTrial(res);
        }
    }
}
```
Для вызова F# методов в C# требуется только вызвать их с global(и то, насколько я знаю, у некоторых работало и без него). Чтобы декомпилировать в C# я использовал dotPeek.
При декомпиляции в C# Discriminated Union превращается в абстрактный класс, и из-за этого код значительно раздувается. Сам код декомпиляции лежит
