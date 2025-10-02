# Changelog

All notable changes to CheermateApp will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Phase 1 Implementation (v1.1) - December 2024

#### Added

**Authentication & Security**
- BCrypt password hashing for secure password storage
- Password verification using BCrypt in AuthRepository
- Forgot password flow with security questions
- Password reset functionality with new password dialog
- Input validation utility for username, email, and password
- SQL injection pattern detection and prevention
- Security questions stored in database
- User security answers saved during registration

**User Profile Management**
- Profile editing dialog for username, email, first name, last name
- Change password functionality with current password verification
- Profile update with validation and uniqueness checks
- Real-time profile reload after updates

**SubTask System**
- SubTaskAdapter for RecyclerView display
- Subtask completion tracking with checkboxes
- Visual feedback for completed subtasks (strikethrough, opacity)
- Progress calculation based on completed subtasks
- Delete subtask functionality
- Subtask item layout with Material Design

**Task Reminders & Notifications**
- NotificationUtil for creating and managing notifications
- Notification channel setup for Android O+
- ReminderManager for scheduling reminders with AlarmManager
- ReminderReceiver broadcast receiver for handling alarms
- Support for exact alarms with battery optimization handling
- Notification icon (bell)
- Reminder dialog layout with quick options and custom time picker

**Testing**
- InputValidationUtilTest with 25+ test cases
- PasswordHashUtilTest with 9 test cases
- Unit test coverage >90% for utility classes

**Documentation**
- PHASE1_IMPLEMENTATION.md - Complete implementation guide
- CHANGELOG.md - Project changelog
- Inline documentation for all new classes and methods

#### Changed
- SignUpActivity: Now hashes passwords before storage
- SignUpActivity: Enhanced validation with InputValidationUtil
- SignUpActivity: Saves security answers during registration
- AuthRepository: Updated to use BCrypt for password verification
- MainActivity: Added profile editing and password change functionality
- CheermateApp: Initialize notification channel on startup
- AndroidManifest.xml: Added notification and alarm permissions
- AndroidManifest.xml: Registered ReminderReceiver

#### Fixed
- gradle/libs.versions.toml: Updated AGP version for compatibility
- settings.gradle.kts: Simplified repository configuration

#### Security
- Implemented BCrypt password hashing (cost factor 12)
- Added SQL injection pattern detection
- Input sanitization for user inputs
- Parameterized database queries (Room already provides this)
- Secure password comparison using BCrypt

---

## [1.0.0] - 2024

### Initial Release

#### Added
- User authentication with login
- Task CRUD operations
- Task priority and status management
- Personality-based user system
- Dashboard with statistics
- Task filtering and search
- Room database implementation
- Material Design UI with glass morphism effects
- Bottom navigation
- Task adapter with RecyclerView

#### Features
- User registration
- Task creation and management
- Priority levels (Low, Medium, High)
- Status tracking (Pending, In Progress, Completed)
- Due date and time setting
- Task progress tracking
- Settings fragment
- Personality selection

---

## Release Notes

### Version 1.1 (Phase 1) - Upcoming

**What's New:**
- 🔐 Secure password hashing with BCrypt
- 🔑 Forgot password recovery with security questions
- ✏️ Edit your profile information
- 🔒 Change your password securely
- ✅ SubTask support for breaking down tasks
- 🔔 Task reminders with notifications
- 🛡️ Enhanced input validation and security

**Improvements:**
- Better security with encrypted passwords
- SQL injection prevention
- Comprehensive unit tests
- Improved code documentation

**Bug Fixes:**
- Fixed Gradle build configuration
- Fixed repository setup issues

**Known Issues:**
- SubTask UI needs integration into FragmentTaskActivity
- Reminder UI needs integration into task creation
- Profile picture upload not yet implemented

---

## Migration Guide

### Upgrading from v1.0 to v1.1

#### Database Changes
- No schema changes required
- Existing passwords in plain text should be migrated to BCrypt hashes
- Security questions need to be populated in database

#### Code Changes
- Update any password validation logic to use `InputValidationUtil`
- Replace direct password comparison with `PasswordHashUtil.verifyPassword()`
- Add notification permissions request in appropriate activities

#### Dependencies
- Add BCrypt library: `implementation("at.favre.lib:bcrypt:0.10.2")`
- Ensure Material Components library is up to date

---

## Future Versions

### Version 1.5 (Phase 2) - Q2 2025
- Dark mode support
- Recurring tasks
- Task templates
- Advanced analytics
- Import/Export functionality
- Widgets

### Version 2.0 (Phase 3) - Q3 2025
- Cloud sync
- Multi-device support
- Collaboration features
- Web application
- Real-time sync

### Version 2.x+ (Phase 4) - Q4 2025+
- AI-powered features
- Natural language task creation
- Third-party integrations
- Gamification
- Premium features

---

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## Support

For support, please check:
- [TESTING_CHECKLIST.md](TESTING_CHECKLIST.md) - Testing guidelines
- [PHASE1_IMPLEMENTATION.md](PHASE1_IMPLEMENTATION.md) - Implementation details
- [TODO.md](TODO.md) - Known issues and planned features
- [GitHub Issues](https://github.com/JTaguiamdev/CheermateApp/issues)

---

**Maintainers:**
- JTaguiamdev

**License:** [Add your license here]

**Last Updated:** December 2024
