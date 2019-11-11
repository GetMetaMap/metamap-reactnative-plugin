import PropTypes from "prop-types";
import { requireNativeComponent, ViewPropTypes } from "react-native";
var viewProps = {
  name: "MatiLoginButton",
  propTypes: {
    Text: PropTypes.string,
    ...ViewPropTypes
  }
};

export default requireNativeComponent("MatiLoginButton", viewProps);
