<h1>TMDB</h1>
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2FgVZIvphd_400x400.jpg?alt=media&token=f2ab7a4d-1067-4922-963d-666d2e869fc4" width="25%"></img>

----------------------------------------------------------------

MovieDB es una aplicación de cliente para [TMDb](https://www.themoviedb.org) en Android, creada con Kotlin.

[![Master branch](https://travis-ci.com/haroldadmin/MovieDB.svg?branch=master)](https://travis-ci.com/haroldadmin/MovieDB.svg?branch=master)

## Arquitectura
* Arquitectura MVP
* Utiliza componentes de la arquitectura de Android [viewBinding, Room].
* Interfaz de usuario limpia, intuitiva, creada usando las directrices de Material Design
* Utiliza [RxJava](https://github.com/ReactiveX/RxJava) para llamadas de red, transformaciones y observación de bases de datos.
* Completamente fuera de línea. TMDB usa [Room Database](https://developer.android.com/topic/libraries/architecture/room) para administrar una base de datos SQLite local, lo que significa que si ya vio una vez alguna pelicula, esta quedara almacenada y podra verla asi como su detalle incluso si no tiene acceso a la red, cabe señalar que en la primera vez que abre el App al navegar entre las peliculas, estas se iran descargando por paginas al llegar al final del scroll de la pantalla.
* Utiliza [Retrofit](https://square.github.io/retrofit/) para realizar llamadas a la API.
* Utiliza [Glide](https://github.com/bumptech/glide) para cargar imágenes.
* Construido sobre una arquitectura de actividad única. Cada pantalla de la aplicación es un fragmento (menos la actividad principal que es el padre de los fragmentos).
* Utiliza Service para el control en background y foreground
* Se hace uso de librerias como Google Maps, Firebase [Fire Storange y Firestore Database]

## Características
* Visualización del actor mas popular asi como peliculas en las que ha participado
* Búsqueda de películas
* Filtro de peliculas para mostrar segun su popularidad, puntuación o las peliculas proximas a estrenarse
* Vea los detalles de la película, como la fecha de lanzamiento, la descripción general, su puntuación
* Funciona sin conexión mediante el almacenamiento local en base de datos
* Envio de coordenadas a Firebase para su almacenamiento en FireStore
* Manejo de Google Maps para visualizar en tiempo real la ubicación del dispositivo y la fecha de creación
* Toma una foto o buscala en tu galeria y subela a Firebase Storage

## Proximas Funciones
* Posibilidad de agregar peliculas como favoritas.
* Inicio de sesión mediante usuario, contraseña y redes sociales
* Migración a la arquitectura MVVM
* Posibilidad de ver imagenes almacenadas en Firebase dentro de una pantalla adicional

## Screenshots
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen1.png?alt=media&token=91a31b1b-938c-4cc7-8db9-bb6f56e8b676" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen2.png?alt=media&token=49629ae5-ab14-4ab4-b648-6d92493c948a" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen3.png?alt=media&token=c7491d8a-7cee-417e-8fdd-e17677732809" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen4.png?alt=media&token=2f9d06e4-e88b-4bae-b224-e0ce12f8a73f" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen5.png?alt=media&token=7e5d30b0-8411-440c-9cc1-b0d4e0cabc6e" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen6.png?alt=media&token=702e8fd5-30b1-4d8b-8165-a42b6574ca34" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen7.png?alt=media&token=a9e3ebaa-7b29-4ed4-8e58-91244554b10e" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen8.png?alt=media&token=dd235443-281f-4956-9263-63ac4905ee8c" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen9.png?alt=media&token=768760c1-bb72-468b-8bf6-896307fcc4be" width="30%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen_images.png?alt=media&token=d0b220a7-6110-4541-aa41-944e894e7300" width="100%"></img> 
<img src="https://firebasestorage.googleapis.com/v0/b/tmdb-bbe2a.appspot.com/o/resources%2Fscreen_location.png?alt=media&token=18dcc2ca-3f26-428e-97df-9f5aa60378d1" width="100%"></img> 
