// This file is part of the "SQLTap" project
//   (c) 2011-2013 Paul Asmuth <paul@paulasmuth.com>
//
// Licensed under the MIT License (the "License"); you may not use this
// file except in compliance with the License. You may obtain a copy of
// the License at: http://opensource.org/licenses/MIT

package com.paulasmuth.sqltap

class PrettyJSONWriter extends RequestVisitor {

  val INDENT = "  "
  var ind = 1

  val buf = new StringBuffer

  def run : Unit = {
    buf.append("[\n")

    //for (ind <- (0 until req.stack.root.next.length))
    //  next(req.stack.root, ind)

    buf.append("\n]")
  }

  private def next(cur: Instruction, index: Int) : Unit = {/*
    var scope : String = null

    if (index != 0)
      buf.append(",\n")

    if (cur.name == "execute")
      { write("{\n"); scope = "}"; ind += 1; }

    if (cur.name == "findMulti") {
      if (cur.next.length == 0)
        write(json(cur.relation.output_name) + ": []")
      else {
        write(json(cur.relation.output_name) + ": [\n")
        scope = "]"; ind += 1
      }

    } else if (cur.name == "countMulti") {
      write(json(cur.relation.output_name) + ": ")
      write(cur.record.get("__count"))

    } else {
      if (cur.name == "findSingle") {
        write(json(cur.relation.name) + ": {\n")
        scope = "}"; ind += 1
      }

      if (cur.record != null) {
        write(json("__resource") + ": " +
          json(cur.record.resource.name))

        for ((field, ind) <- cur.record.fields.zipWithIndex) {
          buf.append(",\n")
          write(json(field) + ": " + json(cur.record.data(ind)))
        }

        if (cur.next.length > 0)
          buf.append(",\n")
      }
    }

    if (cur.name == "execute" && cur.prev == null)
      next(cur.next(index), index)

    else
      for ((nxt, nxt_ind) <- cur.next.zipWithIndex)
        next(nxt, nxt_ind)

    if (scope != null)
      { buf.append("\n"); ind -= 1; write(scope) }

  */}

  private def json(str: String) : String =
    if (str == null) "null" else
      "\"" + JSONHelper.escape(str) + "\""

  private def write(str: String) : Unit =
    if (str != null) buf.append((INDENT * ind) + str)

}
