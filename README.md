# git-cqrsJava7
Es un pequeño y simple framework que implementa ciertas funcionalidades básicas del patrón CQRS.

El core de la aplicación consiste en que por cada acción(sea un comando o un query),el core se encarga de invocar al 
handler reponsable de atender a esa acción para realizar las acciones necesarias en base a los datos encapsulados que 
contiene el  objecto comando/query.
Los clase que representan comandos o querys, son clases Java POJO , que heredan de la clase abstract ResponsibilityTask.
Con esto el core sabe que esas clases representan acciones que serán atendidas por algún handler.
EL core cuenta con dos executors encargados de manejar la concurrencia para los commands y otro para los queries. La forma 
de invocar un mpetodo del core para ejecutar uan acción es por medio de la clase CqrsInterface, que brinda el único punto de
acceso a los métodos accesibles del core para el resto de la aplicación.


Referencias:
http://martinfowler.com/bliki/CQRS.html
