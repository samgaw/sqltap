#!/bin/bash

if [[ "$SCHEMA_URL" != "" ]]; then
  curl -sL "$SCHEMA_URL" -o /var/schema.xml
fi

opts=""
opts="$opts --config '${SQLTAP_SCHEMA}'"
opts="$opts --http ${SQLTAP_HTTP_PORT}"
opts="$opts --disable-keepalive"
opts="$opts -t ${SQLTAP_THREADS}"
opts="$opts --mysql-host ${MYSQL_HOST}"
opts="$opts --mysql-port ${MYSQL_PORT}"
opts="$opts --mysql-user ${MYSQL_USER}"
opts="$opts --mysql-database ${MYSQL_DATABASE}"
opts="$opts --mysql-numconns ${MYSQL_NUMCONNS}"
opts="$opts --mysql-queuelen ${MYSQL_QUEUELEN}"
opts="$opts --cache-backend ${CACHE_BACKEND}"
if [[ "${CACHE_BACKEND}" == "memcache" ]]; then
  opts="$opts --memcache-host ${MEMCACHE_HOST}"
  opts="$opts --memcache-port ${MEMCACHE_PORT}"
  opts="$opts --memcache-queuelen ${MEMCACHE_QUEUELEN}"
  opts="$opts --memcache-numconns ${MEMCACHE_NUMCONNS}"
fi
opts="$opts ${SQLTAP_OPTS}"

exec java \
    -Djava.rmi.server.hostname="${RMI_BIND}" \
    -Dcom.sun.management.jmxremote \
    -Dcom.sun.management.jmxremote.port="${JMX_PORT}" \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Xmx"${JAVA_XMX}" -XX:GCTimeRatio=99 -XX:+UseConcMarkSweepGC \
    -jar "${SQLTAP_JARFILE}" \
    $opts
