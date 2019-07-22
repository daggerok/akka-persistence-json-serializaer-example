package com.github.daggerok.akka.persistence

import akka.serialization.JSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

case object Json {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  //mapper.configure(SerializationFeature.INDENT_OUTPUT, false)
  //mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
  //import com.fasterxml.jackson.annotation.JsonInclude.Include
  //mapper.setSerializationInclusion(Include.NON_NULL)
  //mapper.setSerializationInclusion(Include.NON_ABSENT)
}

class MyJsonSerializer extends JSerializer {
  import Json._
  override protected def fromBinaryJava(bytes: Array[Byte], manifest: Class[_]): AnyRef = mapper.readValue(bytes)
  override def identifier: Int = 1234567
  override def toBinary(o: AnyRef): Array[Byte] = mapper.writeValueAsBytes(o)
  override def includeManifest: Boolean = false
}
