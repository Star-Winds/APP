#!/usr/bin/env bash
set -euo pipefail

APK_SOURCE="../app/build/outputs/apk/debug/app-debug.apk"
APK_TARGET="./meifei.apk"

if [ ! -f "$APK_SOURCE" ]; then
  echo "APK not found: $APK_SOURCE"
  echo "请先运行: ./gradlew assembleDebug"
  exit 1
fi

cp "$APK_SOURCE" "$APK_TARGET"
echo "已生成 $APK_TARGET"
