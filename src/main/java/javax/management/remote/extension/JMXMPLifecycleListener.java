package javax.management.remote.extension;

import java.lang.management.ManagementFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class JMXMPLifecycleListener implements LifecycleListener
{
    private static final Log log = LogFactory.getLog(JMXMPLifecycleListener.class);

    protected int port = 5555;

    protected JMXConnectorServer cs;


    public int getPort()
    {
        return port;
    }


    public void setPort(final int port)
    {
        this.port = port;
    }


    @Override
    public void lifecycleEvent(final LifecycleEvent event)
    {

        try
        {

            // START
            if (Lifecycle.START_EVENT == event.getType())
            {
                log.debug("Start JMXMP");

                cs = JMXConnectorServerFactory.newJMXConnectorServer(
                    new JMXServiceURL("jmxmp", "0.0.0.0", port),
                    null,
                    ManagementFactory.getPlatformMBeanServer()
                );
                cs.start();

                log.info("Started JMXMP on port " + port);
            }

            // STOP
            else if (Lifecycle.STOP_EVENT == event.getType())
            {
                log.debug("Stopp JMXMP");

                cs.stop();

                log.info("Stopped JMXMP");
            }

        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
