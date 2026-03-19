# Matatag OS — Java Smartphone Simulator

A Java-based smartphone operating system simulator built as an Object-Oriented Programming project. Matatag OS simulates core smartphone behaviors through a structured, layered architecture spanning apps, managers, models, and UI — complete with a boot sequence that logs every system initialization step and internal state change.

---

## Structure
```
src/
├── apps/
│   ├── base/          # App<M, U> generic base, Command system
│   ├── preinstalled/  # FileManagerApp, DocsApp, CameraApp, CalculatorApp, PhoneApp, AppStoreApp
│   ├── store/         # MusicPlayerApp, BuggyApp (downloadable apps)
│   └── system/        # HomeApp, SettingsApp, BatteryApp, AppManagerApp
├── core/
│   ├── AppLoader      # Wires and registers apps into AppManager
│   ├── BootLoader     # Entry point, launches SmartphoneSystem
│   ├── PhoneState     # Read-only facade over SettingsManager
│   └── SmartphoneSystem  # Orchestrates init and main loop
├── manager/
│   ├── base/          # Base Manager class
│   ├── preinstalled/  # PhoneManager, AudioManager
│   └── system/        # AppManager, SettingsManager, BatteryManager, FileManager
├── model/
│   ├── base/          # File base model
│   ├── preinstalled/  # Contact, CallLog, TextThread, Audio, Photo, Video, Document
│   └── system/        # SettingsModel, Battery
├── network/           # WifiNetwork, WifiScanner, MobileData
└── ui/
    ├── base/          # UI base class with input handling
    ├── preinstalled/  # AppStoreUI
    └── system/        # HomeUI, SettingsUI, BatteryUI, AppManagerUI
```

---

## Extensibility

Matatag OS is designed to be easy to extend. Here's what you can add without touching existing code:

**New Apps**
Extend `App<M, U>`, create a config class with name/developer/version/type/memory constants, define your `Runnable[]` commands and `BooleanSupplier[]` checks, then register it in `SmartphoneSystem.initApps()`.

**New Network Types**
The `network` package is isolated — add new network models alongside `WifiNetwork` and `MobileData` without affecting the rest of the system.

**New File Types**
Extend the `File` base class in `model.base` and add handling in `FileManager` — the file manager app will pick it up automatically.

**New Predefined Data**
Add entries to `PredefinedContacts`, `PredefinedSongs`, or `PredefinedWifiNetworks` — no other code needs to change.

**App Store Integration**
The `AppStoreApp` already supports installing and uninstalling apps at runtime. New downloadable apps just need to be added to the available apps list in `SmartphoneSystem.initApps()`.
