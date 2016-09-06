/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.planeshift;

import java.util.UUID;

/**
 *
 * @author fwannmacher
 */
public class SkyshipWeatherlight2 extends SkyshipWeatherlight1 {

    public SkyshipWeatherlight2(UUID ownerId) {
        super(ownerId);
        this.cardNumber = "133b";
    }

    public SkyshipWeatherlight2(final SkyshipWeatherlight2 card) {
        super(card);
    }

    @Override
    public SkyshipWeatherlight2 copy() {
        return new SkyshipWeatherlight2(this);
    }

}
