package com.swmansion.rnscreens

import com.facebook.react.bridge.JSApplicationIllegalArgumentException
import com.facebook.react.common.MapBuilder
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import com.swmansion.rnscreens.ScreenViewManager

@ReactModule(name = ScreenViewManager.REACT_CLASS)
class ScreenViewManager : ViewGroupManager<Screen>() {
  override fun getName(): String {
    return REACT_CLASS
  }

  override fun createViewInstance(reactContext: ThemedReactContext): Screen {
    return Screen(reactContext)
  }

  @ReactProp(name = "active", defaultFloat = 0)
  fun setActive(view: Screen, active: Float) {
    view.isActive = active != 0f
  }

  @ReactProp(name = "stackPresentation")
  fun setStackPresentation(view: Screen, presentation: String) {
    if ("push" == presentation) {
      view.stackPresentation = Screen.StackPresentation.PUSH
    } else if ("modal" == presentation || "containedModal" == presentation || "fullScreenModal" == presentation || "formSheet" == presentation) { // at the moment Android implementation does not handle contained vs regular modals
      view.stackPresentation = Screen.StackPresentation.MODAL
    } else if ("transparentModal" == presentation || "containedTransparentModal" == presentation) { // at the moment Android implementation does not handle contained vs regular modals
      view.stackPresentation = Screen.StackPresentation.TRANSPARENT_MODAL
    } else {
      throw JSApplicationIllegalArgumentException("Unknown presentation type $presentation")
    }
  }

  @ReactProp(name = "stackAnimation")
  fun setStackAnimation(view: Screen, animation: String?) {
    if (animation == null || "default" == animation) {
      view.stackAnimation = Screen.StackAnimation.DEFAULT
    } else if ("none" == animation) {
      view.stackAnimation = Screen.StackAnimation.NONE
    } else if ("fade" == animation) {
      view.stackAnimation = Screen.StackAnimation.FADE
    }
  }

  @ReactProp(name = "gestureEnabled", defaultBoolean = true)
  fun setGestureEnabled(view: Screen, gestureEnabled: Boolean) {
    view.isGestureEnabled = gestureEnabled
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any>? {
    return MapBuilder.of<String, Map<String, String>>(
            ScreenDismissedEvent.EVENT_NAME,
            MapBuilder.of("registrationName", "onDismissed"),
            ScreenAppearEvent.EVENT_NAME,
            MapBuilder.of("registrationName", "onAppear"),
            StackFinishTransitioningEvent.EVENT_NAME,
            MapBuilder.of("registrationName", "onFinishTransitioning"))
  }

  companion object {
    const val REACT_CLASS = "RNSScreen"
  }
}