with open("app/src/main/res/values/themes.xml", "r") as f:
    text = f.read()
text = text.replace('parent="android:Theme.DeviceDefault.NoActionBar"', 'parent="Theme.AppCompat.DayNight.NoActionBar"')
with open("app/src/main/res/values/themes.xml", "w") as f:
    f.write(text)
