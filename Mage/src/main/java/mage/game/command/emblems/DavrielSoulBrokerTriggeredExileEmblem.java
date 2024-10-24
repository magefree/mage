package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerTriggeredExileEmblem extends Emblem {

    // You get an emblem with "Whenever you draw a card, exile the top two cards of your library."
    public DavrielSoulBrokerTriggeredExileEmblem() {
        super("Emblem Davriel");

        Ability ability = new DrawCardControllerTriggeredAbility(
                Zone.COMMAND,
                new ExileCardsFromTopOfLibraryControllerEffect(2),
                false);
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerTriggeredExileEmblem(final DavrielSoulBrokerTriggeredExileEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerTriggeredExileEmblem copy() {
        return new DavrielSoulBrokerTriggeredExileEmblem(this);
    }
}
