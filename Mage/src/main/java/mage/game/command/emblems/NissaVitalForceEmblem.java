package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class NissaVitalForceEmblem extends Emblem {
    // You get an emblem with "Whenever a land enters the battlefield under your control, you may draw a card."

    public NissaVitalForceEmblem() {
        super("Emblem Nissa");
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.COMMAND, new DrawCardSourceControllerEffect(1), new FilterControlledLandPermanent("a land"),
                true, null, true);
        getAbilities().add(ability);
    }

    private NissaVitalForceEmblem(final NissaVitalForceEmblem card) {
        super(card);
    }

    @Override
    public NissaVitalForceEmblem copy() {
        return new NissaVitalForceEmblem(this);
    }
}
