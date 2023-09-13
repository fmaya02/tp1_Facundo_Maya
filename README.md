# TP1 Facundo Maya
 Repositorio para la entrega del TP1 de Desarrollo de Software

## Desarrollo
Para modelar el diagrama de clases, primero se creó una EntidadBase, a la cual se le implementó la interfaz "Serializable", y se definió en ella el atributo "id", con las anotaciones correspondientes para que fuese una clave primaria autoincrementable. Luego se crearon el resto de entidades del diagrama, y todas ellas heredan de EntidadBase. Además, se utilizaron las notaciones de lombok y JPA, tales como @Entity, @Data, @Builder, etc.

Una vez creadas las entidades, se crearon los repositorios, utilizando las anotaciones @Repository.

Por último, se creó en el main() un nuevo método con CommandLineRunner, que se encarga de crear datos de prueba tanto para el modelo de objetos como para la persistencia en la base de datos. Se crearon: 1 rubro (con 3 productos pertenecientes a él), 2 clientes (cada uno con 1 domicilio), 2 pedidos (uno de ellos con 1 detalle y el otro con 2, cada detalle referenciando un producto). Todo esto se guarda en la base de datos.

## Conclusión
Desarrollar este TP fue útil para comprender el funcionamiento del "cascade" de operaciones de JPA. Un problema que se encontró fue que los pedidos no guardaban el "cliente_id", a pesar de que se les asignaba el cliente y se los persistía. Al investigar, me di cuenta de que el atributo, si bien en base de datos está en pedido, en el modelo de objetos está en cliente, por lo que esto se resolvió agregando una nueva persistencia de los clientes al final del método.

Las anotaciones de relación @OneToMany y @ManyToOne fueron muy útiles para poder centrarse en el modelado de objetos, dejando la conversión al modelo de Entidad-Relación al ORM, y comprobando que ésta conversión se realizaba correctamente, de acuerdo a las anotaciones utilizadas.

Como observación extra, cabe destacar que tuve un bloqueo debido a que, en IntelliJ, el método builder(), junto con los demás métodos agregados por anotaciones, no se reconocían. Repasando la teoría, esto tiene sentido, ya que son inyecciones de dependencias en tiempo de ejecución, por lo que el programa funciona correctamente a pesar de las advertencias del IDE.

## Alumno
**Nombre:** Facundo Maya
**Curso:** 3K10
**Legajo:** 48997
