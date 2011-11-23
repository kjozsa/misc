/**
 * an illustrated dependency injection alternative using the cake pattern in scala
 * @author kjozsa
 */
case class User(name: String, password: String)

trait UserDaoComponent {
  val userDao: UserDao // abstract value

  trait UserDao { // "interface" as an abstract trait
    def findByName(name: String): Option[User]
  }

  class FakeUserDao extends UserDao { // one possible implementation
    def findByName(name: String) = Some(User("boci", "b0c1k4"))
  }

  class NoneUserDao extends UserDao { // other implementation
    def findByName(name: String) = None
  }
}

trait AuthenticationServiceComponent { this: UserDaoComponent => // selftype annotation
  val authenticationService: AuthenticationService

  class AuthenticationService {
    def authenticate(username: String, password: String) = {
      userDao.findByName(username).filter(_.password == password) // "injected" userDao
    }
  }
}

trait DefaultEnvironment extends AuthenticationServiceComponent with UserDaoComponent { // one possible configuration
  val authenticationService = new AuthenticationService
  val userDao = new FakeUserDao // instantiation of specific component 
}

trait NoneEnvironment extends AuthenticationServiceComponent with UserDaoComponent { // other possible configuration
  val authenticationService = new AuthenticationService
  val userDao = new NoneUserDao
}

// main obj
object CakeTest extends App with DefaultEnvironment {
  object WithNoneEnvironment extends NoneEnvironment {
    println("with NoneEnvironment: ")
    println(authenticationService.authenticate("boci", "wrongpass"))
    println(authenticationService.authenticate("boci", "b0c1k4"))
  }

  println("with DefaultEnvironment: ")
  println(authenticationService.authenticate("boci", "wrongpass"))
  println(authenticationService.authenticate("boci", "b0c1k4"))

  WithNoneEnvironment
}

