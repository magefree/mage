package mage.game.command.emblems;

import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class TezzeretBetrayerOfFleshEmblem extends Emblem {

    // −6: You get an emblem with "Whenever an artifact you control becomes tapped, draw a card."
    public TezzeretBetrayerOfFleshEmblem() {
        super("Emblem Tezzeret");
        this.getAbilities().add(new BecomesTappedTriggeredAbility(
                Zone.COMMAND, new DrawCardSourceControllerEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, false
        ));
    }

    private TezzeretBetrayerOfFleshEmblem(final TezzeretBetrayerOfFleshEmblem card) {
        super(card);
    }

    @Override
    public TezzeretBetrayerOfFleshEmblem copy() {
        return new TezzeretBetrayerOfFleshEmblem(this);
    }
}
