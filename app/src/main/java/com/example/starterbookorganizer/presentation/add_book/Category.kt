package com.example.starterbookorganizer.presentation.add_book

/**
 * Created by AidenChang on 2025/9/1
 */
enum class Category {
    READ,
    READING,
    WISHLIST,
    UNKNOWN;

    companion object {

        /**
         * 將字串名稱轉換為對應的 [Category]。
         *
         * - 如果 [name] 為 `null`，則回傳 [UNKNOWN]。
         * - 如果 [name] 能成功對應 enum 名稱 (例如 "READ", "READING")，則回傳對應值。
         */
        fun fromName(name: String?): Category {
            return if (name == null) {
                UNKNOWN
            } else {
                valueOf(name)
            }
        }
    }
}