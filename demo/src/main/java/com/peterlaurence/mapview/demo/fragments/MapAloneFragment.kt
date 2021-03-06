package com.peterlaurence.mapview.demo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peterlaurence.mapview.MapView
import com.peterlaurence.mapview.MapViewConfiguration
import com.peterlaurence.mapview.core.TileStreamProvider
import com.peterlaurence.mapview.demo.R

/**
 * An example showing the simplest usage of [MapView].
 */
class MapAloneFragment : Fragment() {
    private lateinit var parentView: ViewGroup

    /**
     * The [MapView] should always be added inside [onCreateView], to ensure state save/restore.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false).also {
            parentView = it as ViewGroup
            context?.let { ctx ->
                makeMapView(ctx)?.addToFragment()
            }
        }
    }

    /**
     * Assign an id the the [MapView] (it's necessary to enable state save/restore).
     */
    private fun MapView.addToFragment() {
        id = R.id.mapview_id
        isSaveEnabled = true

        parentView.addView(this, 0)
    }

    /**
     * In this example, the configuration is done **immediately** after the [MapView] is added to
     * the view hierarchy, in [onCreateView].
     * But it's not mandatory, it could have been done later on. But beware to configure only once.
     */
    private fun makeMapView(context: Context): MapView? {
        val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
            try {
                context.assets?.open("tiles/esp/$zoomLvl/$row/$col.jpg")
            } catch (e: Exception) {
                null
            }
        }
        val tileSize = 256
        val config = MapViewConfiguration(
            5, 8192, 8192, tileSize, tileStreamProvider
        ).setMaxScale(2f)

        return MapView(context).apply {
            configure(config)
        }
    }
}