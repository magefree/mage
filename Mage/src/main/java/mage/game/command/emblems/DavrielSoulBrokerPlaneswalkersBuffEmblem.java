package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerPlaneswalkersBuffEmblem extends Emblem {

    private static final FilterControlledPlaneswalkerPermanent filter = new FilterControlledPlaneswalkerPermanent();

    static {
        filter.add(SubType.DAVRIEL.getPredicate());
    }

    // You get an emblem with "Davriel planeswalkers you control have "+2: Draw a card.""
    public DavrielSoulBrokerPlaneswalkersBuffEmblem() {
        super("Emblem Davriel");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND,
                new GainAbilityControlledEffect(
                        new LoyaltyAbility(new DrawCardSourceControllerEffect(1), +2),
                        Duration.EndOfGame, filter
                ).setText("Davriel planeswalkers you control have \"+2: Draw a card.\""));
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerPlaneswalkersBuffEmblem(final DavrielSoulBrokerPlaneswalkersBuffEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerPlaneswalkersBuffEmblem copy() {
        return new DavrielSoulBrokerPlaneswalkersBuffEmblem(this);
    }
}
