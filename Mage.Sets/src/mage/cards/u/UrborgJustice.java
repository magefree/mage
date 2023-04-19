
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author andyfries
 */
public final class UrborgJustice extends CardImpl {

    public UrborgJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        // Target opponent sacrifices a creature for each creature put into your graveyard from the battlefield this turn.
        SacrificeEffect sacrificeEffect = new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new UrborgJusticeDynamicValue(), "");
        sacrificeEffect.setText("Target opponent sacrifices a creature for each creature put into your graveyard from the battlefield this turn");

        this.getSpellAbility().addEffect(sacrificeEffect);
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private UrborgJustice(final UrborgJustice card) {
        super(card);
    }

    @Override
    public UrborgJustice copy() {
        return new UrborgJustice(this);
    }
}

class UrborgJusticeDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurnByOwner(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public UrborgJusticeDynamicValue copy() {
        return new UrborgJusticeDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "creature put into your graveyard from the battlefield this turn";
    }
}
