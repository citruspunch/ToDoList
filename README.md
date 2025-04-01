# Extended ToDo List App

This is a simple ToDo list application built using Jetpack Compose. It allows users to add tasks, attach images to tasks, edit tasks, and delete tasks.

## Features

- Add new tasks with text input
- Attach images to tasks from the device's gallery
- Display tasks in a list
- Edit existing tasks, including text and attached images
- Delete tasks from the list
- Modern UI using Material3 components

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/citruspunch/ToDoList.git
   ```
2.  **Open in Android Studio:**
    - Open Android Studio and select "Open an Existing Project".
    - Navigate to the cloned directory and select it.

3.  **Build the project:**
    - Android Studio should automatically start building the project. If not, you can manually trigger a build by selecting "Build" -> "Make Project".

4.  **Run the app:**
    - Connect an Android device or start an emulator.
    - Click the "Run" button in the toolbar.
  
  ## Code Usage Examples

### Adding a Task

To add a new task, enter the task description in the text field labeled "Add new task".  You can optionally attach an image by clicking the "Attach Image" button and selecting an image from your device's gallery. Finally, click the "Add Task" button to add the task to the list.

### Editing a Task

To edit a task, click the "Edit" icon next to the task. An `AlertDialog` will appear, allowing you to modify the task text and attach a new image.  Click "Save" to update the task or "Cancel" to discard changes.

### Deleting a Task

To delete a task, click the "Delete" icon next to the task. The task will be removed from the list immediately.

## Short Demo Video

[ToDoList App Demo](https://drive.google.com/file/d/14Ybd_XHl9GWWVdGDG-IcTaCiDXEitYIp/view?usp=sharing)
