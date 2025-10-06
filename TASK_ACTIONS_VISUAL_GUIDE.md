# Visual Guide - Task Actions Menu

## What You'll See

### 1. Toolbar with 3-Dot Menu Icon
```
┌─────────────────────────────────────┐
│  [←]  Task                    [⋮]  │  ← 3-dot menu icon added here
└─────────────────────────────────────┘
```

When the user taps the 3-dot icon (⋮), a bottom sheet appears.

### 2. Bottom Sheet Dialog
```
┌─────────────────────────────────────┐
│                                     │
│         (Content scrolls up)        │
│                                     │
│                                     │
╞═════════════════════════════════════╡
│         Task Actions                │  ← Header
├─────────────────────────────────────┤
│  ✅   Mark as Completed             │  ← Option 1
├─────────────────────────────────────┤
│  ⏰   Snooze                         │  ← Option 2
├─────────────────────────────────────┤
│  ❌   Won't Do                       │  ← Option 3
└─────────────────────────────────────┘
```

### 3. Action Flows

#### Mark as Completed Flow
```
Tap [⋮] → Bottom Sheet → Tap "Mark as Completed"
    ↓
Confirmation Dialog: "Are you sure you want to mark '[Task Title]' as completed?"
    ↓
[Yes] → Task Status = "Completed"
        Task Progress = 100%
        Toast: "✅ Task marked as completed!"
        UI refreshes
    ↓
[Cancel] → Returns to task details
```

#### Snooze Flow
```
Tap [⋮] → Bottom Sheet → Tap "Snooze"
    ↓
Snooze Options Dialog:
    - ⏰ 1 hour
    - ⏰ 1 day
    - ⏰ 3 days
    - ⏰ 1 week
    - ⏰ Custom
    ↓
Select Option → Task Due Date Updated
                Toast: "⏰ Task snoozed for [duration]"
                UI refreshes with new due date
    ↓
Custom → Date Picker → Select Future Date
                     → Task Due Date Updated
                       Toast: "⏰ Task snoozed until [date]"
```

#### Won't Do Flow
```
Tap [⋮] → Bottom Sheet → Tap "Won't Do"
    ↓
Confirmation Dialog: "Are you sure you want to mark '[Task Title]' as Won't Do?"
    ↓
[Yes] → Task Status = "Cancelled"
        Toast: "❌ Task marked as Won't Do"
        UI refreshes
    ↓
[Cancel] → Returns to task details
```

## UI Screenshots Description

### Before Implementation
```
┌─────────────────────────────────────┐
│  [←]  Task                          │  ← No menu icon
└─────────────────────────────────────┘
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Overdue Badge (if overdue)  │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Task Details Card          │   │
│  │  • Title                    │   │
│  │  • Description              │   │
│  │  • Category, Priority, Date │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Subtasks Card              │   │
│  └─────────────────────────────┘   │
└─────────────────────────────────────┘
```

### After Implementation
```
┌─────────────────────────────────────┐
│  [←]  Task                    [⋮]  │  ← Menu icon added!
└─────────────────────────────────────┘
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Overdue Badge (if overdue)  │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Task Details Card          │   │
│  │  • Title                    │   │
│  │  • Description              │   │
│  │  • Category, Priority, Date │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Subtasks Card              │   │
│  └─────────────────────────────┘   │
└─────────────────────────────────────┘
       ↓ (User taps [⋮])
┌─────────────────────────────────────┐
│         Task Actions                │
├─────────────────────────────────────┤
│  ✅   Mark as Completed             │
├─────────────────────────────────────┤
│  ⏰   Snooze                         │
├─────────────────────────────────────┤
│  ❌   Won't Do                       │
└─────────────────────────────────────┘
   ↑ Bottom sheet appears here
```

## Key Features

### Material Design Compliance
- ✅ Uses standard Material Design Bottom Sheet
- ✅ Ripple effect on action items
- ✅ Proper elevation and shadows
- ✅ Consistent with app's glass card styling

### User Experience
- ✅ Clear visual icons (emojis) for each action
- ✅ Descriptive text labels
- ✅ Confirmation dialogs for destructive actions
- ✅ Toast feedback for all actions
- ✅ Smooth animations
- ✅ Easy to dismiss (tap outside or select action)

### Functionality
- ✅ Mark task as completed (with confirmation)
- ✅ Snooze with multiple preset durations
- ✅ Snooze with custom date picker
- ✅ Mark task as won't do/cancelled (with confirmation)
- ✅ All actions update database immediately
- ✅ UI refreshes to show changes

## Testing the Implementation

### Test Checklist
1. **Open task details**
   - Navigate to any task in the app
   - Verify 3-dot menu icon appears in toolbar

2. **Open bottom sheet**
   - Tap the 3-dot icon
   - Verify bottom sheet slides up from bottom
   - Verify all 3 options are visible

3. **Test Mark as Completed**
   - Tap "Mark as Completed"
   - Verify confirmation dialog appears
   - Tap "Yes"
   - Verify task status updates to "Completed"
   - Verify success toast appears

4. **Test Snooze**
   - Tap 3-dot icon → Snooze
   - Test each preset option (1 hour, 1 day, etc.)
   - Verify due date updates
   - Test custom date picker
   - Verify minimum date is today

5. **Test Won't Do**
   - Tap 3-dot icon → Won't Do
   - Verify confirmation dialog appears
   - Tap "Yes"
   - Verify task status updates to "Cancelled"
   - Verify success toast appears

6. **Test Dismissal**
   - Open bottom sheet
   - Tap outside the sheet
   - Verify it dismisses
   - Tap back button
   - Verify it dismisses

## Status

✅ **Implementation Complete and Ready for Testing**

The feature is fully implemented following Android best practices and Material Design guidelines. The code is production-ready and includes proper error handling and user feedback.
