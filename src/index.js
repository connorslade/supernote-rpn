import { Image, NativeModules, AppRegistry } from "react-native";
import { PluginManager } from "sn-plugin-lib";

import { name } from "../PluginConfig.json";

const { Module } = NativeModules;

AppRegistry.registerComponent(name, () => () => null);
PluginManager.init();

PluginManager.registerButton(1, ["NOTE", "DOC"], {
  id: 0,
  name: "RPN Calculator",
  icon: Image.resolveAssetSource(require("../assets/icon.png")).uri,
  showType: 0,
});

PluginManager.registerButtonListener({
  onButtonPress(event) {
    if (event?.id == 0) Module?.toggle();
  },
});
