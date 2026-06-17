# Setup do Ambiente de Desenvolvimento Java

Guia de instalação do Java 21 e Maven 3.9.8 no Windows 11 via PowerShell.

---

## Pré-requisitos

- Windows 10/11
- PowerShell 5.1+
- `winget` disponível (vem pré-instalado no Windows 11)

---

## 1. Java 21 (Microsoft OpenJDK)

```powershell
winget install Microsoft.OpenJDK.21 --accept-source-agreements --accept-package-agreements
```

O instalador pedirá permissão de administrador. Aguarde a conclusão.

**Instalado em:** `C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot`

---

## 2. Apache Maven 3.9.8

O Maven não está disponível via `winget`, então instalamos manualmente:

```powershell
# Download
$mavenVersion = "3.9.8"
$mavenUrl = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
Invoke-WebRequest -Uri $mavenUrl -OutFile "$env:TEMP\maven.zip" -UseBasicParsing

# Extração
New-Item -ItemType Directory -Force "C:\tools\maven" | Out-Null
Expand-Archive -Path "$env:TEMP\maven.zip" -DestinationPath "C:\tools\maven" -Force
```

**Instalado em:** `C:\tools\maven\apache-maven-3.9.8`

---

## 3. Variáveis de Ambiente

Configuradas para o usuário atual (não precisa de admin):

```powershell
# JAVA_HOME
[Environment]::SetEnvironmentVariable(
    "JAVA_HOME",
    "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot",
    "User"
)

# Adicionar Maven ao PATH
$userPath = [Environment]::GetEnvironmentVariable("PATH", "User")
[Environment]::SetEnvironmentVariable(
    "PATH",
    "$userPath;C:\tools\maven\apache-maven-3.9.8\bin",
    "User"
)
```

> **Atenção:** feche e abra um novo terminal após configurar as variáveis.

---

## 4. Verificação

Em um novo terminal:

```powershell
java -version
# openjdk version "21.0.11" 2026-04-21 LTS
# OpenJDK Runtime Environment Microsoft-13877171 (build 21.0.11+10-LTS)

mvn -version
# Apache Maven 3.9.8
# Java version: 21.0.11, vendor: Microsoft
```

---

## 5. Teste com o projeto

```powershell
cd C:\ClaudeCodes\learning-java\java-fundamentals
mvn compile exec:java -Dexec.mainClass="br.com.orderhub.fundamentals.Dia01_TiposEColecoes"
mvn compile exec:java -Dexec.mainClass="br.com.orderhub.fundamentals.Dia02_StreamsEOptional"
```

---

## Versões instaladas

| Ferramenta         | Versão       |
|--------------------|--------------|
| Java (MS OpenJDK)  | 21.0.11 LTS  |
| Apache Maven       | 3.9.8        |
| Sistema Operacional| Windows 11   |
