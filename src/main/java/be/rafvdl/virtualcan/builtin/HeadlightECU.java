package be.rafvdl.virtualcan.builtin;

import be.rafvdl.virtualcan.bus.Listener;
import be.rafvdl.virtualcan.bus.Message;
import be.rafvdl.virtualcan.ecu.ECU;

import static be.rafvdl.virtualcan.util.BinaryUtil.isBitHigh;

@ECU
public class HeadlightECU {

    private final byte side;

    private boolean indicator = false;

    private boolean dipBeam = false;
    private boolean highBeam = false;
    private boolean frontFogLamp = false;

    /**
     * @param side 0 if left, 1 if right
     */
    public HeadlightECU(byte side) {
        this.side = side;
    }

    @Listener(BuiltinMessages.INDICATOR)
    public void indicator(Message message) {
        indicator = message.getData()[0] == (101 + side) || message.getData()[0] == 103;
    }

    @Listener(BuiltinMessages.LIGHT)
    public void light(Message message) {
        byte D0 = message.getData()[0];
        frontFogLamp = isBitHigh(D0, 2);
        highBeam = isBitHigh(D0, 1);
        dipBeam = isBitHigh(D0, 0);
    }

    public boolean isIndicator() {
        return indicator;
    }

    public boolean isDipBeam() {
        return dipBeam;
    }

    public boolean isHighBeam() {
        return highBeam;
    }

    public boolean isFrontFogLamp() {
        return frontFogLamp;
    }

}
