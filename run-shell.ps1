<#
.SYNOPSIS
    Build and launch XMage with the modern UI shell enabled (Windows / PowerShell).

.DESCRIPTION
    One-shot helper for eyeball-testing the opt-in modern shell. It:
      1. Checks for the prerequisites (JDK, Maven) and offers to install missing ones via winget.
      2. Builds all modules (mvn clean install -DskipTests).
      3. Packages the runnable client + server zips and unpacks them to a run folder.
      4. Starts the server, waits for it to accept connections, then launches the client
         with the shell flags (-Dxmage.shell=1 [-Dxmage.shell.theme=dark|light]).

    Run it from the repository root (the folder containing pom.xml and SHELL.md).
    If PowerShell blocks the script, launch it as:
        powershell -ExecutionPolicy Bypass -File .\run-shell.ps1

.PARAMETER Theme
    Shell variant: 'dark' (default) or 'light'.

.PARAMETER NoShell
    Launch the stock (Nimbus) client instead — useful as the A/B baseline.

.PARAMETER SkipBuild
    Skip the build + package steps and just (re)launch using the existing run folder.

.PARAMETER RunDir
    Where to unpack and run from. Default: %USERPROFILE%\xmage-run.

.EXAMPLE
    .\run-shell.ps1                 # build, then launch dark shell
    .\run-shell.ps1 -Theme light    # build, then launch light shell
    .\run-shell.ps1 -SkipBuild      # relaunch without rebuilding
    .\run-shell.ps1 -NoShell        # launch stock client (baseline)
#>

[CmdletBinding()]
param(
    [ValidateSet('dark', 'light')]
    [string]$Theme = 'dark',
    [switch]$NoShell,
    [switch]$SkipBuild,
    [string]$RunDir = (Join-Path $env:USERPROFILE 'xmage-run')
)

$ErrorActionPreference = 'Stop'

# --- helpers ---------------------------------------------------------------

function Write-Step($msg) { Write-Host "`n==> $msg" -ForegroundColor Cyan }
function Write-Ok($msg)   { Write-Host "    $msg" -ForegroundColor Green }
function Write-Warn($msg) { Write-Host "    $msg" -ForegroundColor Yellow }
function Fail($msg)       { Write-Host "`nERROR: $msg" -ForegroundColor Red; exit 1 }

function Refresh-Path {
    # winget-installed tools land in the machine/user PATH but not the current session.
    $machine = [Environment]::GetEnvironmentVariable('Path', 'Machine')
    $user    = [Environment]::GetEnvironmentVariable('Path', 'User')
    $env:Path = (@($machine, $user) | Where-Object { $_ }) -join ';'
}

function Have($cmd) { [bool](Get-Command $cmd -ErrorAction SilentlyContinue) }

function Ensure-Winget {
    if (-not (Have 'winget')) {
        Fail "winget is not available to auto-install prerequisites. Install JDK 21 (adoptium.net) and Maven (maven.apache.org) manually, then re-run with -SkipBuild once built."
    }
}

function Ensure-Tool($displayName, $probeCmd, $wingetId) {
    if (Have $probeCmd) { Write-Ok "$displayName found."; return }
    Write-Warn "$displayName not found - installing via winget ($wingetId)..."
    Ensure-Winget
    & winget install --id $wingetId -e --accept-source-agreements --accept-package-agreements
    Refresh-Path
    if (-not (Have $probeCmd)) {
        Fail "$displayName still not on PATH after install. Close and reopen PowerShell, then re-run this script (the install likely succeeded but needs a fresh session)."
    }
    Write-Ok "$displayName installed."
}

function Wait-ForPort($port, $timeoutSec) {
    $sw = [System.Diagnostics.Stopwatch]::StartNew()
    while ($sw.Elapsed.TotalSeconds -lt $timeoutSec) {
        try {
            $client = New-Object System.Net.Sockets.TcpClient
            $client.Connect('127.0.0.1', $port)
            if ($client.Connected) { $client.Close(); return $true }
        } catch {
            Start-Sleep -Milliseconds 800
        }
    }
    return $false
}

# --- locate the repo -------------------------------------------------------

$repo = if ($PSScriptRoot) { $PSScriptRoot } else { (Get-Location).Path }
if (-not (Test-Path (Join-Path $repo 'pom.xml'))) {
    Fail "Run this script from the XMage repo root (no pom.xml found in '$repo')."
}
Write-Step "Repository: $repo"

# --- prerequisites ---------------------------------------------------------

Write-Step 'Checking prerequisites'
# 'javac' (not just 'java') proves a JDK rather than a JRE is installed.
Ensure-Tool 'JDK (javac)' 'javac' 'EclipseAdoptium.Temurin.21.JDK'
Ensure-Tool 'Maven (mvn)' 'mvn'   'Apache.Maven'

if (-not $env:MAVEN_OPTS) {
    $env:MAVEN_OPTS = '-Xmx2g'
    Write-Ok "Set MAVEN_OPTS=$($env:MAVEN_OPTS) for this build."
}

# --- build + package -------------------------------------------------------

if (-not $SkipBuild) {
    Write-Step 'Building all modules (mvn clean install -DskipTests) - this can take a while'
    Push-Location $repo
    try {
        & mvn clean install -DskipTests
        if ($LASTEXITCODE -ne 0) { Fail 'Build failed. Scroll up for the first [ERROR] line.' }

    } finally {
        Pop-Location
    }

    Write-Step 'Packaging runnable server + client'
    Push-Location (Join-Path $repo 'Mage.Server')
    try {
        & mvn package assembly:single -DskipTests
        if ($LASTEXITCODE -ne 0) { Fail 'Server packaging failed.' }
    } finally { Pop-Location }
    Push-Location (Join-Path $repo 'Mage.Client')
    try {
        & mvn package assembly:single -DskipTests
        if ($LASTEXITCODE -ne 0) { Fail 'Client packaging failed.' }
    } finally { Pop-Location }
    Write-Ok 'Build + package complete.'
} else {
    Write-Warn 'Skipping build (-SkipBuild).'
}

# --- unpack ----------------------------------------------------------------

$serverDir = Join-Path $RunDir 'server'
$clientDir = Join-Path $RunDir 'client'

if (-not $SkipBuild) {
    Write-Step "Unpacking to $RunDir"
    $serverZip = Join-Path $repo 'Mage.Server\target\mage-server.zip'
    $clientZip = Join-Path $repo 'Mage.Client\target\mage-client.zip'
    if (-not (Test-Path $serverZip)) { Fail "Server zip not found at $serverZip" }
    if (-not (Test-Path $clientZip)) { Fail "Client zip not found at $clientZip" }

    foreach ($d in @($serverDir, $clientDir)) {
        if (Test-Path $d) { Remove-Item $d -Recurse -Force }
        New-Item -ItemType Directory -Path $d -Force | Out-Null
    }
    Expand-Archive -Path $serverZip -DestinationPath $serverDir -Force
    Expand-Archive -Path $clientZip -DestinationPath $clientDir -Force
    Write-Ok 'Unpacked.'
}

# Locate the jars (version-agnostic, and tolerant of the zip extracting into a subfolder).
$serverJar = Get-ChildItem -Path $serverDir -Recurse -Filter 'mage-server-*.jar' -ErrorAction SilentlyContinue | Select-Object -First 1
$clientJar = Get-ChildItem -Path $clientDir -Recurse -Filter 'mage-client-*.jar' -ErrorAction SilentlyContinue | Select-Object -First 1
if (-not $serverJar) { Fail "Server jar not found under $serverDir. Run once without -SkipBuild." }
if (-not $clientJar) { Fail "Client jar not found under $clientDir. Run once without -SkipBuild." }

# The app expects to run from the folder that CONTAINS lib/ (it reads ./config, ./plugins, etc.).
$serverRoot = $serverJar.Directory.Parent.FullName
$clientRoot = $clientJar.Directory.Parent.FullName

# --- launch server ---------------------------------------------------------

Write-Step 'Starting server (new window)'
Start-Process -FilePath 'java' `
    -ArgumentList @('-Xmx1024m', '-jar', "`"$($serverJar.FullName)`"") `
    -WorkingDirectory $serverRoot

Write-Host '    Waiting for server on port 17171...' -ForegroundColor Yellow
if (-not (Wait-ForPort 17171 90)) {
    Fail 'Server did not start listening on port 17171 within 90s. Check the server window for errors.'
}
Write-Ok 'Server is up.'

# --- launch client ---------------------------------------------------------

$clientArgs = @(
    '-Xmx2000m',
    '-Dfile.encoding=UTF-8',
    '-Dsun.jnu.encoding=UTF-8',
    '-Djava.net.preferIPv4Stack=true'
)
if ($NoShell) {
    Write-Step 'Starting STOCK client (shell off - baseline)'
} else {
    Write-Step "Starting client with modern shell ($Theme)"
    $clientArgs += '-Dxmage.shell=1'
    $clientArgs += "-Dxmage.shell.theme=$Theme"
}
$clientArgs += @('-jar', "`"$($clientJar.FullName)`"")

Start-Process -FilePath 'java' -ArgumentList $clientArgs -WorkingDirectory $clientRoot

Write-Host ""
Write-Ok 'Client launched.'
Write-Host @"

Next steps in the client window:
  1. Connect to server  : localhost   port 17171   (any username/password)
  2. Create a table, add an AI/computer opponent, and Start.
  3. Eyeball the battlefield: phase icons, command buttons, player panel density,
     and the collapsed chat strip on the right (its badge should pulse on new messages).

A/B baseline: re-run with  -NoShell  to compare against stock XMage.
Switch variant:            -Theme light
Relaunch without building: -SkipBuild
"@ -ForegroundColor Gray
