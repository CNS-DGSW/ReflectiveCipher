# ReflectiveCipher
> ReflectiveCipher is a Reflection Encrypt Library forked base on 
> [Reflcypt](https://github.com/CNS-DGSW/Reflcrypt).
 The main focus of this project is adding new features and patches
> while also keeping up to date with the original project <br>
> library's default key-algorithm is `PBKDF2WithHmacSHA256`

## How To Use
### Simple implementation <br>
In your security field, add the code below 
```java
public class Secret {
    
    @SecurityField      // encrypt and decrypt this field.
    private String field;
    
    @EncryptParams      // encrypt 'topSecret' parameter
    public void vulnerableMethod(@SecurityParam String topSecret) {
        // ...
    }
    
    @DecryptReturns     // decrypt and return 'NonSecret' object
    public NonSecret info() {
        // ...
    }
    
}
```

## Properties
### [for field] `@SecurityField`
> Assign field to encrypt or decrypt 
* support types:
  * String
  * byte[] or Byte[]

### [for Param] `@SecurityParam`
> Assign parameter to encrypt or decrypt
* support types:
  * String
  * byte[] or Byte[]

### [for Method] `@EncryptParams`
> Encrypt argument, encrypt all field in argument
* useful in JPA Repository.
  * ex) `.save()`
    * when entity save, will encrypt

### [for Method] `@DecryptParams`
> Decrypt argument, decrypt all field in argument

### [for Method] `@EncryptReturns`
> Encrypt return object

### [for Method] `@DecryptReturns`
> Decrypt return object
* useful in JPA Repository
  * when method call from JPA Repository, decrypt entity and return

## Springboot Application properties <br>
Springboot properties prefix is `cipher`
### `algorithm`
> Set cipher-algorithm such as **'AES/CBC/PKCS5Padding'**
* Must be choose in [Java Security Standard Algorithm Name](https://docs.oracle.com/javase/9/docs/specs/security/standard-names.html#security-algorithm-implementation-requirements)
  * The class is Cipher

### `key`
> Set secret-key

### `hash`
> Set hash value

### `iv`
> Set Initialization Vector value
* Length is must be 16 bytes long 

## Collaboration
It adheres to Java's standard conventions, it's flexible enough for many variations, 
and I'm sure you can improve and add more features to the API in an easy-to-use way, 
so feel free to post ideas, issues, and pull requests.

## Licence
ReflectiveCipher is available under the MIT License.


## 🧑‍💻 Thanks to the original developer
* **[이승민 (Eric Lee)](https://github.com/ericlee05)** - CNS 3기 백엔드팀
