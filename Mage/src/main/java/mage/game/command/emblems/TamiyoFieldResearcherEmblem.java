
package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;

/*
 *
 * Author: spjspj
 */
public final class TamiyoFieldResearcherEmblem extends Emblem {
    // You may cast nonland cards from your hand without paying their mana costs.

    public TamiyoFieldResearcherEmblem() {

        this.setName("Emblem Tamiyo");

        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new CastFromHandWithoutPayingManaCostEffect()));

        this.setExpansionSetCodeForImage("EMN");
    }
}
