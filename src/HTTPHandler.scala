package com.paulasmuth.dpump

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler

class HTTPHandler extends AbstractHandler {

  def handle(target: String, base_req: Request, req: HttpServletRequest, res: HttpServletResponse) {
    res.setStatus(200)
    val db_res = DPump.db_pool.execute("select version();")
    res.getWriter().write(db_res.data.head(0))
    base_req.setHandled(true)
  }

}