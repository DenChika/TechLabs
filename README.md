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
Для начала, нужно создать срр файл и подключить dll библиотеку, а также перевести конфигурацию с Debug на Release. В конце исходника нужно написать extern "C", а внутри методы, которые мы хотим чтоб вызывались в Java и C#. __declspec (dllexport) - требование для каждого dll метода. Без этой приписки работать не будет. __stdcall - требование для Java, чтобы вызвать метод из dll. Лично у меня метод называется с припиской Java_"имя класса". В источнике, с которого я брал информацию, было сказано, что это тоже требование для вызова в Java, однако я знаю людей, у которых работало и без этого. В C# приписка __stdcall необязательна. 

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
Нужно сбилдить проект и скопировать путь на dll файл
```cs
```
