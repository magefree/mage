package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class LockAndLoad extends CardImpl {

    private static final DynamicValue xValue = new IntPlusDynamicValue(1, LockAndLoadValue.instance);

    public LockAndLoad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Draw a card, then draw a card for each other instant and sorcery spell you've cast this turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText("Draw a card, then draw a card for each other instant and sorcery spell you've cast this turn"));

        // Plot {3}{U}
        this.addAbility(new PlotAbility("{3}{U}"));
    }

    private LockAndLoad(final LockAndLoad card) {
        super(card);
    }

    @Override
    public LockAndLoad copy() {
        return new LockAndLoad(this);
    }
}

enum LockAndLoadValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher == null ? 0 :
                watcher.getSpellsCastThisTurn(sourceAbility.getControllerId())
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(s -> s.isInstantOrSorcery(game))
                        .filter(s -> !s.getSourceId().equals(sourceAbility.getSourceId())
                                || s.getZoneChangeCounter(game) != sourceAbility.getSourceObjectZoneChangeCounter())
                        .mapToInt(x -> 1)
                        .sum();
    }

    @Override
    public LockAndLoadValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "Number of other instant and sorcery spell you've cast this turn";
    }
}