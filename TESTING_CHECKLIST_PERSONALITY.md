# Testing Checklist - Personality Selection Fix

## Prerequisites
- [ ] App is built and installed on device/emulator
- [ ] User is logged in
- [ ] User has access to Settings screen

## Test Case 1: Verify All 5 Personalities Are Shown

### Steps:
1. Open the app and navigate to Settings (bottom navigation)
2. Tap on the "Personality" row (with compass icon)
3. Observe the dialog that appears

### Expected Result:
✅ Dialog shows "Choose Your Personality" title  
✅ All 5 personality options are visible:
   - Kalog
   - Gen Z
   - Softy
   - Grey
   - Flirty

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 2: Select a New Personality

### Steps:
1. Open personality selection dialog (from Test Case 1)
2. Select "Kalog"
3. Tap "OK"
4. Observe the UI updates

### Expected Result:
✅ Toast message: "✅ Personality updated to Kalog!"  
✅ `tvCurrentPersona` shows: "Kalog"  
✅ `chipPersona` shows: "Kalog Personality"

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 3: Verify Current Selection Is Highlighted

### Steps:
1. Select a personality (e.g., "Softy")
2. Close and reopen the personality selection dialog
3. Observe which option is selected

### Expected Result:
✅ "Softy" has a radio button checked/highlighted  
✅ All other options are unchecked

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 4: Verify MainActivity Updates

### Steps:
1. In Settings, select "Gen Z" personality
2. Navigate back to Home (bottom navigation)
3. Observe the Personality Card on the dashboard

### Expected Result:
✅ `personalityTitle` shows: "Gen Z Vibes"  
✅ `personalityDesc` shows: "Tech-savvy and trendy with the latest slang!"

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 5: Change Personality Multiple Times

### Steps:
1. Select "Kalog" → Verify updates
2. Select "Grey" → Verify updates
3. Select "Flirty" → Verify updates
4. Return to Home and verify each time

### Expected Result:
✅ Each selection updates immediately in Settings  
✅ MainActivity reflects changes when returning from Settings  
✅ No errors or crashes occur

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 6: Cancel Personality Selection

### Steps:
1. Open personality selection dialog
2. Select a different personality (don't confirm yet)
3. Tap "Cancel"
4. Reopen the dialog

### Expected Result:
✅ Previous personality selection is still shown  
✅ Temporary selection was not saved

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 7: First-Time User (No Personality Selected)

### Steps:
1. Create a new user account
2. Skip personality selection (if allowed)
3. Go to Settings
4. Open personality selection dialog

### Expected Result:
✅ All 5 personalities are shown  
✅ No personality is pre-selected (all radio buttons unchecked)  
✅ User can select any personality

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Test Case 8: Verify Database Consistency

### Steps:
1. Select "Softy" personality
2. Close the app completely
3. Reopen the app
4. Check Settings and MainActivity

### Expected Result:
✅ Personality persists after app restart  
✅ Settings shows "Softy"  
✅ MainActivity shows "Softy Vibes" and description

### Actual Result:
- [ ] PASS
- [ ] FAIL (describe issue): _______________

---

## Edge Cases

### Test Case 9: Rapid Selection Changes
**Steps:** Quickly select different personalities in succession  
**Expected:** No crashes, last selection wins  
**Result:** [ ] PASS / [ ] FAIL

### Test Case 10: Background/Foreground
**Steps:** Select personality, put app in background, return  
**Expected:** Selection is saved and visible  
**Result:** [ ] PASS / [ ] FAIL

---

## Summary

**Total Test Cases:** 10  
**Passed:** ___  
**Failed:** ___  

**Overall Status:** [ ] READY FOR PRODUCTION / [ ] NEEDS FIXES

**Notes:**
_______________________________________________
_______________________________________________
_______________________________________________
