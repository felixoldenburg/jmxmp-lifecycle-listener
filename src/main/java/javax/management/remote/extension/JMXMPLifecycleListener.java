package javax.management.remote.extension;

import java.lang.management.ManagementFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class JMXMPLifecycleListener implements LifecycleListener
{
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
                System.out.println("Start JMXMP on port " + port);

                cs = JMXConnectorServerFactory.newJMXConnectorServer(
                    new JMXServiceURL("jmxmp", "0.0.0.0", port),
                    null,
                    ManagementFactory.getPlatformMBeanServer()
                );
                cs.start();

                System.out.println("Started JMXMP");

            }

            // STOP
            else if (Lifecycle.STOP_EVENT == event.getType())
            {

                System.out.println("Stop JMXMP");

                cs.stop();
            }

        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }

}
