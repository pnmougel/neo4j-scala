package models.enums

import com.fasterxml.jackson.core.`type`.TypeReference


/**
  * Created by nico on 13/10/17.
  */
object MyEnum extends Enumeration {
  type MyEnum = Value
  val New, AsNew, VeryGood, Good, Correct = Value
}

/**
  * Proxy class required for json serialization
  */
class MyEnumType extends TypeReference[MyEnum.type]
