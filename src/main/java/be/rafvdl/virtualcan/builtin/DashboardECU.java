package be.rafvdl.virtualcan.builtin;

import be.rafvdl.virtualcan.bus.Listener;
import be.rafvdl.virtualcan.bus.Message;
import be.rafvdl.virtualcan.ecu.ECU;

import static be.rafvdl.virtualcan.util.BinaryUtil.isBitHigh;

@ECU
public class DashboardECU {

    private boolean leftIndicator = false;
    private boolean rightIndicator = false;

    private boolean dipBeam = false;
    private boolean highBeam = false;
    private boolean frontFogLamp = false;
    private boolean rearFogLamp = false;

    @Listener(BuiltinMessages.INDICATOR)
    public void indicator(Message message) {
        leftIndicator = message.getData()[0] == 101 || message.getData()[0] == 103;
        rightIndicator = message.getData()[0] == 102 || message.getData()[0] == 103;
    }

    @Listener(BuiltinMessages.LIGHT)
    public void light(Message message) {
        byte D0 = message.getData()[0];
        rearFogLamp = isBitHigh(D0, 3);
        frontFogLamp = isBitHigh(D0, 2);
        highBeam = isBitHigh(D0, 1);
        dipBeam = isBitHigh(D0, 0);
    }

    public boolean isLeftIndicator() {
        return leftIndicator;
    }

    public boolean isRightIndicator() {
        return rightIndicator;
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

    public boolean isRearFogLamp() {
        return rearFogLamp;
    }

}
