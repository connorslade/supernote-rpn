import { AppRegistry, Image } from "react-native";
import App from "./App";
import { name } from "../PluginConfig.json";

import { PluginManager } from "sn-plugin-lib";

AppRegistry.registerComponent(name, () => App);
PluginManager.init();

PluginManager.registerButton(1, ["NOTE", "DOC"], {
  id: 100,
  name: "RPN Calculator",
  icon: Image.resolveAssetSource(require("../assets/icon.png")).uri,
  showType: 1,
});
