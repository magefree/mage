package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainRetraceYourGraveyardEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class WrennAndSixEmblem extends Emblem {

    public WrennAndSixEmblem() {
        super("Emblem Wrenn");
        // Instant and sorcery cards in your graveyard have retrace.
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND, new GainRetraceYourGraveyardEffect(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY)
        ));
    }

    private WrennAndSixEmblem(final WrennAndSixEmblem card) {
        super(card);
    }

    @Override
    public WrennAndSixEmblem copy() {
        return new WrennAndSixEmblem(this);
    }
}
