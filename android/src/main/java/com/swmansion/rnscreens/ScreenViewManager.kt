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
  companion object {
    const val REACT_CLASS = "RNSScreen"
  }

  override fun getName(): String =
    REACT_CLASS

  override fun createViewInstance(reactContext: ThemedReactContext) =
    Screen(reactContext)

  @ReactProp(name = "active", defaultFloat = 0f)
  fun setActive(view: Screen, active: Float) {
    view.isActive = active != 0f
  }

  @ReactProp(name = "stackPresentation")
  fun setStackPresentation(view: Screen, presentation: String) {
    view.stackPresentation =
      when (presentation) {
        "push" -> Screen.StackPresentation.PUSH

        // at the moment Android implementation does not handle contained vs regular modals
        "modal", "containedModal", "fullScreenModal", "formSheet" -> Screen.StackPresentation.MODAL

        // at the moment Android implementation does not handle contained vs regular modals
        "transparentModal", "containedTransparentModal" -> Screen.StackPresentation.TRANSPARENT_MODAL

        else -> throw JSApplicationIllegalArgumentException("Unknown presentation type $presentation")
      }
  }

  @ReactProp(name = "stackAnimation")
  fun setStackAnimation(view: Screen, animation: String?) {
    when (animation) {
      null, "default" ->
        view.stackAnimation = Screen.StackAnimation.DEFAULT

      "none" ->
        view.stackAnimation = Screen.StackAnimation.NONE

      "fade" ->
        view.stackAnimation = Screen.StackAnimation.FADE
    }
  }

  @ReactProp(name = "gestureEnabled", defaultBoolean = true)
  fun setGestureEnabled(view: Screen, gestureEnabled: Boolean) {
    view.isGestureEnabled = gestureEnabled
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> =
    mutableMapOf(
            ScreenDismissedEvent.EVENT_NAME to mapOf("registrationName" to "onDismissed"),
            ScreenAppearEvent.EVENT_NAME to mapOf("registrationName" to "onAppear"),
            StackFinishTransitioningEvent.EVENT_NAME to mapOf("registrationName" to "onFinishTransitioning")
    )
}