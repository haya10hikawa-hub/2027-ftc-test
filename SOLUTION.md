# SOLUTION

## Issue #1: trclib / ftclib のクラスが見つからずビルドできない

**エラーメッセージ:**
```
error: package trclib.xxx does not exist
error: cannot find symbol
```

**原因:**
`TeamCode/src/main/java/trclib` と `TeamCode/src/main/java/ftclib` は git サブモジュールだが、
clone 時に初期化されておらずディレクトリが空だった (`git submodule status` の先頭が `-`)。

**解決策:**
```
git submodule update --init --recursive
```
その後 Android Studio で Gradle Sync → Build を再実行する。

## Issue #2: FtcTeleOp で `Cannot resolve symbol 'Motor'`

**エラーメッセージ:**
```
Cannot resolve symbol 'Motor'
```

**原因:**
`FtcTeleOp.java` で `Motor.Action` を使っているのに `teamcode.subsystems.Motor` を import していなかった。

**解決策:**
`FtcTeleOp.java` に `import teamcode.subsystems.Motor;` を追加する。
あわせて `case A:` の `break;` 漏れ (case B に処理が流れていた) を修正した。

## 補足: `SDK XML version 4` の警告

```
This version only understands SDK XML versions up to 3 but an SDK XML file of version 4 was encountered.
```

新しい SDK Manager でインストールしたパッケージの `package.xml` が v4 形式で、
AGP 8.13.2 / Android Studio 2025.3 が v3 までしか読めないために出る警告。ビルドには影響しない。
消したい場合は Android Studio を更新する (AGP は FTC SDK 指定のため変更しない)。

## Issue #3: TeleOp INIT で `Attempt to read from null array`

**エラーメッセージ:**
```
java.lang.NullPointerException: Attempt to read from null array
  at ftclib.driverio.FtcDashboard.displayText(FtcDashboard.java:428)
```

**原因:**
アプリ起動時に FTC Dashboard (acmerobotics) が `@Config` クラスを初期化する際、
`teamcode/Dashboard.java` の `DashboardParams.autoChoices = FtcAuto.autoChoices` が `FtcAuto` のメニューを生成し、
まだ存在しない ftclib `FtcDashboard` を取得しようとして失敗していた。
その結果 acmerobotics FtcDashboard が起動せず、1回目の INIT で ftclib `FtcDashboard` の初期化が途中で落ち、
2回目以降は表示行配列が null の「作りかけ」インスタンスが使われていた。

**解決策:**
`teamcode/Dashboard.java` から未使用の `DashboardParams.autoChoices` を削除し、アプリを再起動 (Restart Robot) する。

## Issue #4: `Unable to find a hardware device with name "pinpointOdo"`

**原因:**
`DriveBase.MecanumRobotInfo` が `useDriveBase = false` でも `setPinpointOdometry()` でハードウェアを即時取得していた。

**解決策:**
`setPinpointOdometry(...)` を `if (RobotParams.Preferences.useDriveBase)` で囲む。

## Issue #5: `Attempt to read from field '...FtcRobotBase.driveBase' on a null object reference`

**原因:**
`useDriveBase = false` のとき `DriveBase.robotBase` は null だが、`setDriveOrientation()` / `subsystemAction()` が null チェックなしで使っていた。

**解決策:**
両メソッドの先頭に `if (robotBase == null) return;` を追加する (`cancel()` / `updateStatus()` と同じ方式)。

## Issue #6: エラーは出ないがコントローラーでモーターが回らない

**原因:**
コントローラーの入力が Driver Station から送られていなかった (ログに `##### button=` が一切出ていない)。

**解決策:**
- 組み込みの **Test Gamepad** OpMode でコントローラーの認識を確認する
- REV 公式コントローラー (PS4 配置) は **OPTIONS + ✕** で User 1 に登録する (✕ = A, ○ = B, □ = X, △ = Y)
- ハードウェア名はコンフィグとコードで大文字小文字まで一致させる (`motor`)
