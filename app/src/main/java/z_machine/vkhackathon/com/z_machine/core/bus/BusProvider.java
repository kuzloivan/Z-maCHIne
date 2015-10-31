package z_machine.vkhackathon.com.z_machine.core.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public final class BusProvider {

    private static final Bus BUZZ = new Bus(ThreadEnforcer.MAIN);

    public static Bus getInstance() {
        return BUZZ;
    }

    private BusProvider() {
    }
}
