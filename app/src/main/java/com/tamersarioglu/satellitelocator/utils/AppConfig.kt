package com.tamersarioglu.satellitelocator.utils

object AppConfig {
    const val POSITION_UPDATE_INTERVAL_MS = 3000L
    const val SEARCH_DEBOUNCE_MS = 300L
    const val DOUBLE_BACK_PRESS_INTERVAL_MS = 2000L
    const val ROOT_DESTINATION_ROUTE = "SatelliteList"
    const val DEFAULT_EXIT_MESSAGE = "Çıkmak için tekrar basınız"
    const val DEFAULT_SEARCH_PLACEHOLDER = "Search satellites..."
    const val LOADING_DEFAULT = "Loading..."
    const val LOADING_SATELLITES = "Loading satellites..."
    const val LOADING_SATELLITE_DETAILS = "Loading satellite details..."
    const val ERROR_FAILED_TO_LOAD_SATELLITES = "Failed to load satellites"
    const val ERROR_FAILED_TO_LOAD_SATELLITE_DETAILS =
        "Failed to load satellite details. Please try again."
    const val ERROR_FAILED_TO_LOAD_SATELLITES_CHECK_CONNECTION =
        "Unable to load satellite data. Please check your connection and try again."
    const val ERROR_FAILED_TO_LOAD_POSITION_UPDATES = "Failed to load position updates"
    const val ERROR_UNKNOWN_OCCURRED = "Unknown error occurred"
    const val ERROR_SATELLITE_NOT_FOUND = "Satellite not found"
    const val ERROR_NO_POSITIONS_FOUND = "No positions found for satellite ID"
    const val ERROR_EMPTY_POSITIONS_LIST = "Empty positions list for satellite ID"
    const val ERROR_SATELLITE_DETAIL_NOT_FOUND = "Satellite detail not found for ID"
    const val ERROR_FAILED_TO_LOAD_TITLE = "Failed to Load"
    const val ERROR_FAILED_TO_LOAD_SATELLITES_TITLE = "Failed to Load Satellites"
    const val SUCCESS_POSITION_REFRESHED = "Position refreshed"
    const val EMPTY_NO_RESULTS_TITLE = "No Results Found"
    const val EMPTY_NO_RESULTS_SUBTITLE_TEMPLATE =
        "No satellites match \"%s\". Try a different search term."
    const val EMPTY_NO_SATELLITES_TITLE = "No Satellites Available"
    const val EMPTY_NO_SATELLITES_SUBTITLE = "There are currently no satellites to display."
    const val STATUS_ACTIVE = "Active"
    const val STATUS_PASSIVE = "Passive"
    const val CONTENT_DESC_SEARCH = "Search"
    const val CONTENT_DESC_CLEAR_SEARCH = "Clear search"
    const val CONTENT_DESC_BACK = "Back"
    const val CONTENT_DESC_POSITION = "Position"
    const val LABEL_LAST_POSITION = "Last Position"
    const val COORDINATE_FORMAT = "%.3f"
    const val DATABASE_NAME = "satellite_database"
    const val FILE_SATELLITES_JSON = "satellites.json"
    const val FILE_SATELLITE_DETAIL_JSON = "satellite-detail.json"
    const val FILE_POSITIONS_JSON = "positions.json"
    const val ANIMATION_STATUS_ALPHA = "statusAlpha"
    const val ANIMATION_STATUS_SCALE = "statusScale"

}
