package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class NissaVitalForceEmblem extends Emblem {
    // You get an emblem with "Whenever a land enters the battlefield under your control, you may draw a card."

    public NissaVitalForceEmblem() {
        super("Emblem Nissa");
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.COMMAND,
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_LAND_A, true);
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
