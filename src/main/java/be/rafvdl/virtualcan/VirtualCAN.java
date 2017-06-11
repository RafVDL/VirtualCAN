package be.rafvdl.virtualcan;

import be.rafvdl.virtualcan.builtin.BuiltinMessages;
import be.rafvdl.virtualcan.builtin.DashboardECU;
import be.rafvdl.virtualcan.builtin.HeadlightECU;
import be.rafvdl.virtualcan.builtin.TaillightECU;
import be.rafvdl.virtualcan.bus.Bus;
import be.rafvdl.virtualcan.bus.Message;

public class VirtualCAN {

    public static void main(String[] args) {
        Bus bus = new Bus();

        DashboardECU dashboardECU = new DashboardECU();
        HeadlightECU leftHeadlightECU = new HeadlightECU((byte) 0);
        HeadlightECU rightHeadlightECU = new HeadlightECU((byte) 1);
        TaillightECU leftTaillightECU = new TaillightECU((byte) 0);
        TaillightECU rightTaillightECU = new TaillightECU((byte) 1);
        bus.addDevice(dashboardECU);
        bus.addDevice(leftHeadlightECU);
        bus.addDevice(rightHeadlightECU);
        bus.addDevice(leftTaillightECU);
        bus.addDevice(rightTaillightECU);

        Message indicatorMessage = new Message(BuiltinMessages.INDICATOR, 1, new byte[]{101});
        bus.broadcast(indicatorMessage);
        System.out.println("Front left indicator " + leftHeadlightECU.isIndicator());
        System.out.println("Front right indicator " + rightHeadlightECU.isIndicator());
        System.out.println("Rear left indicator " + leftTaillightECU.isIndicator());
        System.out.println("Rear right indicator " + rightTaillightECU.isIndicator());

        Message lightMessage = new Message(BuiltinMessages.LIGHT, 1, new byte[]{0b00010001});
        bus.broadcast(lightMessage);
        System.out.println("Front left dip beam " + leftHeadlightECU.isDipBeam());
        System.out.println("Front left high beam " + leftHeadlightECU.isHighBeam());
        System.out.println("Front right dip beam " + rightHeadlightECU.isDipBeam());
        System.out.println("Front right high beam " + rightHeadlightECU.isHighBeam());
        System.out.println("Rear left brake lamp " + leftTaillightECU.isBrakeLight());
        System.out.println("Rear right brake lamp " + rightTaillightECU.isBrakeLight());
    }

}
