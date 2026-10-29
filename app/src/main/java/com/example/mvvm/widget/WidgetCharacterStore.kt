package com.example.mvvm.widget

import android.content.Context
import com.example.mvvm.domain.model.CharacterModel

data class WidgetCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)

object WidgetCharacterStore {

    private const val PREFS_NAME =
        "rick_morty_widget"

    private const val KEY_ID =
        "character_id"

    private const val KEY_NAME =
        "character_name"

    private const val KEY_STATUS =
        "character_status"

    private const val KEY_SPECIES =
        "character_species"

    private const val KEY_IMAGE =
        "character_image"

    fun saveCharacter(
        context: Context,
        character: CharacterModel
    ) {

        context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putInt(
                KEY_ID,
                character.id
            )
            .putString(
                KEY_NAME,
                character.name
            )
            .putString(
                KEY_STATUS,
                character.status
            )
            .putString(
                KEY_SPECIES,
                character.species
            )
            .putString(
                KEY_IMAGE,
                character.image
            )
            .apply()

        RickMortyWidgetProvider.updateAll(
            context
        )
    }

    fun getCharacter(
        context: Context
    ): WidgetCharacter? {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val id =
            preferences.getInt(
                KEY_ID,
                -1
            )

        if (id == -1) {
            return null
        }

        return WidgetCharacter(
            id =
                id,

            name =
                preferences.getString(
                    KEY_NAME,
                    ""
                ).orEmpty(),

            status =
                preferences.getString(
                    KEY_STATUS,
                    ""
                ).orEmpty(),

            species =
                preferences.getString(
                    KEY_SPECIES,
                    ""
                ).orEmpty(),

            image =
                preferences.getString(
                    KEY_IMAGE,
                    ""
                ).orEmpty()
        )
    }
}