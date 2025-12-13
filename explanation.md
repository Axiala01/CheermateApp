I have addressed the issue where text in `dialog_add_task` was not visible in light mode due to explicit white color settings.

**Problem:**
Previously, to ensure text visibility in dark mode, `android:textColor` was explicitly set to `@color/white` and `android:textColorHint` to `@color/text_secondary_white_70` in `dialog_add_task.xml`. This made the text invisible against the light background in light mode.

**Fix:**
I have reverted these explicit color definitions back to theme attributes, allowing the text to dynamically adjust according to the active theme (light or dark mode).

-   All `android:textColor="@color/white"` attributes in `dialog_add_task.xml` have been replaced with `android:textColor="?android:attr/textColorPrimary"`.
-   All `android:textColorHint="@color/text_secondary_white_70"` attributes in `dialog_add_task.xml` have been replaced with `android:textColorHint="?android:attr/textColorSecondary"`.

Now, the text in the "Create New Task" dialog should be clearly visible and theme-appropriate in both light and dark modes.