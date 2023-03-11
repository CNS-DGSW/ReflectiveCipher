# ReflectiveCipher
> ReflectiveCipher is a Reflection Cryption Library forked base on 
> [Reflcypt](https://github.com/CNS-DGSW/Reflcrypt).
 The main focus of this project is adding new features and patches
> while also keeping up to date with the original project

## API 목록
### [for field] `@SecurityField`
> Reflcrypt 작동 시 암호화 및 복호화 될 필드를 지정합니다.
* 지원하는 타입:
    * String
    * byte[] 및 Byte[]

### [for Param] `@SecurityParam`
> Reflcrypt 작동 시 암호화 및 복호화 될 필드를 지정합니다.
* 지원하는 타입:
    * String
    * byte[] 및 Byte[]

### [for Method] `@EncryptParams`
> 파라미터로 넘어오는 객체 자체, 혹은 내부의 필드가 암호화됩니다.
* JPA에서 repository의 메서드 호출에 적용하면 유용합니다.
    * `.save()` 메서드에 적용하여 데이터 저장 시 자동으로 암호화되도록

### [for Method] `@DecryptParams`
> 파라미터로 넘어오는 객체 자체, 혹은 내부의 필드가 복호화됩니다.

### [for Method] `@EncryptReturns`
> 리턴되는 객체의 필드가 암호화됩니다.

### [for Method] `@DecryptReturns`
> 리턴되는 객체의 필드가 복호화됩니다.
* JPA에서 repository의 메서드 호출에 적용하면 유용합니다.
    * 메서드 호출 시, 자동으로 내용이 복호화되어 리턴

## 🧑‍💻 contributor
* **[이승민 (Eric Lee)](https://github.com/ericlee05)** - CNS 3기 백엔드팀
* **[임재청 (iqpizza6349)](https://github.com/iqpizza6349)** - CNS 3기 백엔드팀
