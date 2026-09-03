with open("app/src/main/java/com/example/data/AppDatabase.kt", "r") as f:
    text = f.read()

# Add fallbackToDestructiveMigration
text = text.replace('                    "webcode_studio_database"\n                ).build()', '                    "webcode_studio_database"\n                ).fallbackToDestructiveMigration()\n                .build()')

with open("app/src/main/java/com/example/data/AppDatabase.kt", "w") as f:
    f.write(text)
