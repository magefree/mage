package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetOpponent;

/**
 * @author TheElk801
 */
public final class SephirothOneWingedAngelEmblem extends Emblem {

    // you get an emblem with "Whenever a creature dies, target opponent loses 1 life and you gain 1 life."
    public SephirothOneWingedAngelEmblem() {
        super("Emblem Sephiroth");
        Ability ability = new DiesCreatureTriggeredAbility(
                Zone.COMMAND, new LoseLifeTargetEffect(1), false,
                StaticFilters.FILTER_PERMANENT_A_CREATURE, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.getAbilities().add(ability);
    }

    private SephirothOneWingedAngelEmblem(final SephirothOneWingedAngelEmblem card) {
        super(card);
    }

    @Override
    public SephirothOneWingedAngelEmblem copy() {
        return new SephirothOneWingedAngelEmblem(this);
    }
}
