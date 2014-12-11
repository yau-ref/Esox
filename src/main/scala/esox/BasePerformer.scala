package esox

import java.io._

import esox.termops.TerminalOperation

import scala.reflect.ClassTag
import scala.util.Try

class BasePerformer extends Performer {

  override def performSafe[A: ClassTag, B: ClassTag](op: TerminalOperation[A, B]): Try[B] = {
    println(s"I'v got [ ${op.toString} ]")
    //    println(s"Runtime class A = ${classTag[A].runtimeClass}")
    //    println(s"Runtime class B = ${classTag[B].runtimeClass}")


    lazy val loader = ClassLoader.getSystemClassLoader
    val location = "/tmp/Esox/modseq"
    writeObj(op, location)
    val obj = readObj(op.getClass.getName, location, loader).asInstanceOf[TerminalOperation[_, _]]
    println("Res!" + obj.result)


    //new ClassReader(classTag[A].getClass.getName)



    Try(op.result)
  }

  private def writeObj[T](obj: T, location: String) {
    val fos = new FileOutputStream(location)
    val oos = new ObjectOutputStream(fos)
    oos.writeObject(obj)
    oos.close
  }

  private def readObj[T](className: String, location: String, loader: ClassLoader) = {
    val fis = new FileInputStream(location)
    val ois = new ObjectInputStream(fis) {
      override def resolveClass(desc: ObjectStreamClass) = Class.forName(desc.getName, false, loader)
    }
    val obj = ois.readObject()
    ois.close
    obj
  }

}
