package mage.game.command.emblems;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class MuYanlingSkyDancerEmblem extends Emblem {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ISLAND, "islands");

    // "Islands you control have '{T}: Draw a card'."
    public MuYanlingSkyDancerEmblem() {
        super("Emblem Yanling");
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new GainAbilityControlledEffect(new SimpleActivatedAbility(
                        new DrawCardSourceControllerEffect(1), new TapSourceCost()
                ), Duration.EndOfGame, filter)
        ));
    }

    private MuYanlingSkyDancerEmblem(final MuYanlingSkyDancerEmblem card) {
        super(card);
    }

    @Override
    public MuYanlingSkyDancerEmblem copy() {
        return new MuYanlingSkyDancerEmblem(this);
    }
}
