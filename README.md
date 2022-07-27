# AsyncExecution

**AsyncExecution** library is created as a replacement for the deprecated AsyncTask class.

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

---

## Setup

#### Gradle
```gradle
dependencies {
    implementation 'com.github.FikiCarDev:AsyncExecution:1.0'
}
```

#### Maven
```xml
<dependency>
    <groupId>com.github.FikiCarDev</groupId>
    <artifactId>AsyncExecution</artifactId>
    <version>1.0</version>
</dependency>
```

---

## Usage

AsyncExecution library allows you to execute code in the background. 

For example, if you want to make an API call to the server, you have to do it in the background, 
else you would block the "main" thread while waiting for the response and as a result, the app would crash.
This library provides an elegant solution and interface to the background task execution.

You will first have to create a new class that extends virtual AsyncTask<INPUT, PROGRESS, OUTPUT>.
The INPUT is a parameter in doInBackground method that is executing in the background, OUTPUT is the 
value doInBackground should return and PROGRESS is used to communicate with OnProgressListener interface.

You only have to override doInBackground method. **However, I recommened you also override 
onPreExecute, onPostExecute and onBackgroundError.** This way you can notify the user when the task
started and when it ended, and also deal with errors if they occur.


### Example

**Java**
```java
private class RunDemo extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
        // UI elements are available
        tvLabel.setText("PreExecuted");
    }

    @Override
    protected String doInBackground(String msg) throws Exception {
        SystemClock.sleep(2000);
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        // UI elements are available
        StringBuilder tmp = new StringBuilder(tvLabel.getText());
        tmp.append(" ").append(msg).append(" OnPostExecute");
        tvLabel.setText(tmp);
    }

    @Override
    protected void onBackgroundError(Exception e) {
        e.printStackTrace();
    }
}
```

**Kotlin**
```kotlin
private inner class RunDemo : AsyncTask<String?, Void?, String?>() {

    override fun onPreExecute() {
        // UI elements are available
        tvLabel!!.text = "PreExecuted"
    }

    override fun doInBackground(msg: String?): String? {
        SystemClock.sleep(2000)
        return msg
    }

    override fun onPostExecute(msg: String?) {
        // UI elements are available
        val tmp = StringBuilder(tvLabel!!.text)
        tmp.append(" ").append(msg).append(" OnPostExecute")
        tvLabel!!.text = tmp
    }

    override fun onBackgroundError(e: Exception?) {
        e!!.printStackTrace()
    }
}
```

In this example, we update Label from onPreExecute to notify the user about the start of the task,
in doInBackground we do the task, and in onPostExecute we notify the user about the end of the task with
the message passed from the background thread.

To run this code you have to call execute the method and pass all the required parameters.

**Java**
```java
RunDemo runDemo = new RunDemo();
runDemo.execute("Value from Background");
```

**Kotlin**
```kotlin
val runDemo: RunDemo = RunDemo()
runDemo.execute("Value from Background")
```

**Full code can be found in this repository in app folder, and full working demo can be tested by
opening this project in Android Studio.**

Base AsyncTask class also has the following methods you can override and use:
```java
protected void onPreExecute() { }

protected abstract OUTPUT doInBackground(INPUT input) throws Exception;

protected void onPostExecute(OUTPUT output) { }

protected abstract void onBackgroundError(Exception e);

public void setOnProgressListener(OnProgressListener<PROGRESS> onProgressListener) { }

public void setOnCancelledListener(OnCancelledListener onCancelledListener) { }
```
---
## Author

[@FikiCarDev](https://github.com/FikiCarDev)