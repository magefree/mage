package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerGainLifeEmblem extends Emblem {

    // You get an emblem with "Whenever you draw a card, you gain 2 life."
    public DavrielSoulBrokerGainLifeEmblem() {
        super("Emblem Davriel");
        Ability ability = new DrawCardControllerTriggeredAbility(Zone.COMMAND, new GainLifeEffect(2), false);
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerGainLifeEmblem(final DavrielSoulBrokerGainLifeEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerGainLifeEmblem copy() {
        return new DavrielSoulBrokerGainLifeEmblem(this);
    }
}
