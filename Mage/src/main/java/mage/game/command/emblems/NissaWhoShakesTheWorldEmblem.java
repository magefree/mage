package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class NissaWhoShakesTheWorldEmblem extends Emblem {

    public NissaWhoShakesTheWorldEmblem() {
        super("Emblem Nissa");
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new GainAbilityAllEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, false
                )
        ));
    }

    private NissaWhoShakesTheWorldEmblem(final NissaWhoShakesTheWorldEmblem card) {
        super(card);
    }

    @Override
    public NissaWhoShakesTheWorldEmblem copy() {
        return new NissaWhoShakesTheWorldEmblem(this);
    }
}
