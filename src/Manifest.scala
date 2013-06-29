// This file is part of the "SQLTap" project
//   (c) 2011-2013 Paul Asmuth <paul@paulasmuth.com>
//
// Licensed under the MIT License (the "License"); you may not use this
// file except in compliance with the License. You may obtain a copy of
// the License at: http://opensource.org/licenses/MIT

package com.paulasmuth.sqltap

import scala.collection.mutable.{HashMap}

object Manifest {

  private val manifest = HashMap[String,ResourceManifest]()

  def resource(name: String) : ResourceManifest = {
    manifest(name)
  }

  def resources() : List[ResourceManifest] = {
    manifest.values.toList
  }

  def has_resource(name: String) : Boolean = {
    manifest.contains(name)
  }

  def load(cfg_base: java.io.File) : Unit = {
    val sources : Array[String] =
      if (cfg_base.isDirectory unary_!)
        Array(io.Source.fromFile(Config.get('config_base)).mkString)
      else
        cfg_base.list.map(f =>
          io.Source.fromFile(Config.get('config_base) + "/" + f).mkString)

    for (source <- sources){
      val xml = scala.xml.XML.loadString(source)
      var resources = List[scala.xml.Node]()
      var ctrees    = List[scala.xml.Node]()

      if (xml.head.label == "resource")
        resources = List(xml.head)

      else if (xml.head.label == "ctree")
        ctrees = List(xml.head)

      else {
        resources = (xml \ "resource").toList
        ctrees    = (xml \ "ctree").toList
      }

      for (elem <- resources) {
        val resource = new ResourceManifest(elem)
        SQLTap.log_debug("Loading resource: " + resource.name)
        manifest += ((resource.name, resource))
      }

      for (elem <- ctrees) {
        val ctree = new CTree(elem)
        SQLTap.log_debug("Loading ctree: " + ctree.name)
      }
    }

  }

}
