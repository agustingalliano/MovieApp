# Movie App

## Documentación

### Contenido:

* Ui contiene 2 paquetes que son view y viewmodel:
    * View contiene los paquetes fragments, viewholders y el archivo MainActivity:
        * MainActivity es el encargado de manejar los 2 fragments:
            * ListFragment tiene como funcionalidad ir cargando las películas a medida que se recorre la lista.
            * DetailFragment tiene como funcionalidad mostrarnos el detalle de una película y puntuarla.
        * ViewHolders contiene el archivo MovieViewHolder encargado de mostrar los datos en cada item (celda)
        de nuestro listado.
    * ViewModel contiene el archivo MovieViewModel encargado de enlazar el modelo de datos con la view.
* Domain contiene un paquete model el cual contiene 3 archivos (Movie, MovieDetail y Session)
    * Estos 3 archivos son los encargados de contener los datos para entregarlos al viewmodel 
      independientemente de si la información se obtiene de una base de datos o una api.
* Data contiene los paquetes database, model, network y el archivo MovieRepository:
    * Database contiene los paquetes dao y entities:
        * Dao contiene los archivos MovieDetailDao y SessionDao encargados de proveer los métodos necesarios 
          los cuales contienen las queries a hacer para la base de datos.
        * Entities contiene los archivos MovieDetailEntity y SessionEntity encargados de representar el modelo 
          de datos en la base de datos.
    * Model contiene todos los modelos de datos provenientes de la api, entre los que se incluyen MovieModel,
    MovieDetailModel, GenreModel y SessionModel.
    * Network contiene el paquete response y los archivos MovieApiClient, MovieService y SessionService:
        * Detro de response tenemos MovieResponse que representa la respuesta del listado de películas.
        * MovieApiClient es el archivo encargado de proveernos los métodos necesarios que conectan con la api.
        * MovieService y SessionService son los que utilizan esos métodos.
    * MovieRepository es el archivo encargado de decidir si proveernos los datos desde la api o base de datos.
* Adapters contiene el archivo MovieAdapter encargado obtener el la información del listado de películas y 
  almacenarlo en el recyclerview.
* Di contiene los archivos NetworkModule y RoomModule:
    * NetworkModule nos provee la instancia de retrofit.
    * RoomModule nos provee la instancia de room. 
* Listeners contiene 4 archivos necesarios para establecer escucha de eventos, entre ellos están:
    * FragmentCallBackListener para establecer la comunicación entre MainActivity y ListFragment.
    * MovieRepositoryCallBackListener para establecer la comunicación entre MovieViewModel y MovieRepository.
    * OnItemClickListener para establecer la comunicación entre ListFragment y MovieAdapter cuando un item es
      clickeado.
    * SearchCallBackListener para establecer la comunicación entre ListFragment y MovieAdapter como resultado 
      de búsqueda.
* Util contiene 2 archivos:
    * Constants encargado de proveernos algunos valores constantes.
    * ExtensionFunctions encargado de contener los mappers que nos permiten ir transformando la información
      entre la api, base de datos y modelo final.

#### Flujo:
1) Activity Main lanza su primer fragment: ListFragment
2) En este fragment se cargarán las primeras 20 películas populares con título y portada, obtenidas desde la api.
   En caso de no poder obtenerlas, se mostrará un toast para que el usuario reintente ingresando nuevamente. 
   Se cargarán nuevas películas a medida que se recorre la lista. Contiene un buscador en la parte superior 
   el cual filtra las películas que contengan dicho texto. Al presionar un item, se avanza al DetailFragment.
3) DetailFragment va a contener el detalle de esa película, que va a obtener los datos desde el dispositivo 
   si su detalle fue visto con anterioridad, o de la api si es la primera vez que se visualiza. En caso de no 
   poder obtener los datos desde la api, informa con un mensaje para que vuelva a reintentar.
4) Por último, tenemos la posibildad de puntuar la película. Para puntuar una película, existe un EditText que 
   solo acepta los siguientes caracteres: "1234567890", una vez ingresado se presiona en Puntuar. Al momento de 
   puntuar una película, primeramente se verifica que el valor sea correcto, es decir que sea un numero entero o 
   con parte decimal terminada en .5, con valores admitidos en un rango de 0.5 a 10.0. Luego, se genera una 
   sesion de invitado que va a permitir al usuario calificar la película, almacenando el token en el dispositivo
   para utilizar siempre el mismo. En caso de fallar la api para la obtención del token o posteo de puntuación, 
   se le notifcará al usuario con un Toast. Caso contrario, se le informará que la película fue puntuada 
   correctamente.

#### Librerias Utilizadas:

Nombre|Version|Funcionalidad
|----------------|--------|----------------------------------|
|recyclerview    |1.2.1   |Lista Reciclable                  |
|viewmodel  	 |2.4.0   |Enlazado de datos con vistas      |
|livedata		 |2.4.0   |Contenedor de datos observable    |
|activity   	 |1.4.0   |Manejo de acitivities             |
|retrofit 	     |2.6.1   |Cliente HTTP para Android         |
|gson-converter	 |2.6.1   |Convertidor de Retrofit           |
|fragment    	 |1.4.1   |Manejo de fragments               |
|picasso    	 |2.71828 |Descarga en caché de imágenes     |
|room          	 |2.4.0   |Persisntencia de datos            |

### Autor: Agustin Galliano