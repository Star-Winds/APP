$apkSource = "../app/build/outputs/apk/debug/app-debug.apk"
$apkTarget = "./meifei.apk"

if (-Not (Test-Path $apkSource)) {
  Write-Host "APK not found: $apkSource"
  Write-Host "请先运行: ./gradlew assembleDebug"
  exit 1
}

Copy-Item $apkSource $apkTarget -Force
Write-Host "已生成 $apkTarget"
