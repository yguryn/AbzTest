package com.abz.abztest.ui.common

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import com.abz.domain.model.Position

val PositionSaver = Saver<Position?, Bundle>(
    save = { position ->
        Bundle().apply {
            if (position != null) {
                putInt("id", position.id)
                putString("name", position.name)
            }
        }
    },
    restore = { bundle ->
        if (bundle.containsKey("id")) {
            Position(
                id = bundle.getInt("id"),
                name = bundle.getString("name") ?: ""
            )
        } else {
            null
        }
    }
)