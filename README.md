# SimpleTodoNotes

A lightweight notetaking application built with Jetpack Compose using MVVM architechure based on Phillip Lackner's Clean Architecture Note App, but with some modifications and extensions.

Utilizes a Room database to store notes.

Dependency injection is done via Dagger-Hilt.

For navigation, I chose Compose Navigation Reimagined because I found Google's own Navigation-Compose library behaves far too much like a web application and leaves too much room for error despite their previous XML-based navigation library being far more robust.
