import { NativeModules } from "react-native";
import MatiLoginButton from "./MatiLoginButton";

const { MatiGlobalIdSdk } = NativeModules;

module.exports = {
  get MatiGlobalIdSdk() {
    return MatiGlobalIdSdk;
  },
  get MatiLoginButton() {
    return MatiLoginButton;
    //return require("./MatiLoginButton");
  }
};
