package be.rafvdl.virtualcan.builtin;

import be.rafvdl.virtualcan.bus.Listener;
import be.rafvdl.virtualcan.bus.Message;
import be.rafvdl.virtualcan.ecu.ECU;

import static be.rafvdl.virtualcan.util.BinaryUtil.isBitHigh;

@ECU
public class TaillightECU {

    private final byte side;

    private boolean indicator = false;

    private boolean tailLamp = false;
    private boolean brakeLight = false;
    private boolean rearFogLamp = false;

    /**
     * @param side 0 if left, 1 if right
     */
    public TaillightECU(byte side) {
        this.side = side;
    }

    @Listener(BuiltinMessages.INDICATOR)
    public void indicator(Message message) {
        indicator = message.getData()[0] == (101 + side) || message.getData()[0] == 103;
    }

    @Listener(BuiltinMessages.LIGHT)
    public void light(Message message) {
        byte D0 = message.getData()[0];
        brakeLight = isBitHigh(D0, 4);
        rearFogLamp = isBitHigh(D0, 3);
        tailLamp = isBitHigh(D0, 1) || isBitHigh(D0, 0);
    }

    public boolean isIndicator() {
        return indicator;
    }

    public boolean isTailLamp() {
        return tailLamp;
    }

    public boolean isRearFogLamp() {
        return rearFogLamp;
    }

    public boolean isBrakeLight() {
        return brakeLight;
    }

}
