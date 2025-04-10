package com.droidcon.adaptiveinbox.utils

import android.content.Context
import android.graphics.Rect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.droidcon.adaptiveinbox.model.PaneType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(val hingePosition: Rect) : DevicePosture

    data class TableTopPosture(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ) : DevicePosture

    data class Separating(val hingePosition: Rect, var orientation: FoldingFeature.Orientation) :
        DevicePosture
}

fun WindowSizeClass.isCompact() =
    windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
            windowHeightSizeClass == WindowHeightSizeClass.COMPACT

@OptIn(ExperimentalContracts::class)
fun isTableTopPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.HORIZONTAL
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

fun getDisplayFeatures(
    context: Context,
    lifecycle: Lifecycle
): StateFlow<List<DisplayFeature>> {
    return WindowInfoTracker.getOrCreate(context)
        .windowLayoutInfo(context)
        .flowWithLifecycle(lifecycle)
        .map { layoutInfo -> layoutInfo.displayFeatures }
        .stateIn(
            scope = lifecycle.coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
}

fun getDevicePosture(
    context: Context,
    lifecycle: Lifecycle
): StateFlow<DevicePosture> {
    return WindowInfoTracker.getOrCreate(context)
        .getDevicePostureFlow(context, lifecycle)
        .stateIn(
            scope = lifecycle.coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = DevicePosture.NormalPosture
        )
}

fun WindowInfoTracker.getDevicePostureFlow(
    context: Context,
    lifecycle: Lifecycle
): Flow<DevicePosture> {
    return windowLayoutInfo(context)
        .flowWithLifecycle(lifecycle)
        .map { layoutInfo ->
            val foldingFeature =
                layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()
            when {
                isBookPosture(foldingFeature) ->
                    DevicePosture.BookPosture(foldingFeature.bounds)

                isTableTopPosture(foldingFeature) ->
                    DevicePosture.TableTopPosture(foldingFeature.bounds, foldingFeature.orientation)

                isSeparating(foldingFeature) ->
                    DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                else -> DevicePosture.NormalPosture
            }
        }
}

/**
 * This function is used to determine the pane type based on the width size class and device posture.
 * The logic returns the pane type based on the following rules:
 *
 * Tablet:
 * - Portrait -> SINGLE_PANE
 * - Landscape -> ADAPTIVE_PANE
 *
 * Foldable:
 * - Opened
 *  - Any Scape -> ADAPTIVE_PANE
 * - Half Opened
 *  - Any Scape -> DUAL_PANE
 * - Closed
 *  - Portrait -> SINGLE_PANE
 *  - Landscape -> ADAPTIVE_PANE
 *
 * - Phone:
 *  - Portrait -> SINGLE_PANE
 *  - Landscape -> ADAPTIVE_PANE
 */
fun androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.getPaneType(
    foldingDevicePosture: DevicePosture
): PaneType {
    return when (this) {
        androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Compact -> {
            PaneType.SINGLE_PANE
        }

        androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Medium -> {
            if (foldingDevicePosture == DevicePosture.NormalPosture) {
                PaneType.SINGLE_PANE
            } else {
                PaneType.DUAL_PANE
            }
        }

        androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Expanded -> {
            if (foldingDevicePosture == DevicePosture.NormalPosture) {
                PaneType.ADAPTIVE_PANE
            } else {
                PaneType.DUAL_PANE
            }
        }

        else -> PaneType.SINGLE_PANE
    }
}