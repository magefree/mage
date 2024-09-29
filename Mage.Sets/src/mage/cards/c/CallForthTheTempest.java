package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CallForthTheTempest extends CardImpl {

    private static Hint hint = new ValueHint("Total mana value of other spells you've cast this turn", CallForthTheTempestDynamicValue.instance);

    public CallForthTheTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}{R}");

        // Cascade
        this.addAbility(new CascadeAbility(false).setRuleAtTheTop(true));

        // Cascade
        this.addAbility(new CascadeAbility(false).setRuleAtTheTop(true));

        // Call Forth the Tempest deals damage to each creature your opponents control equal to the total mana value of other spells you've cast this turn.
        this.getSpellAbility().addEffect(new DamageAllEffect(
                CallForthTheTempestDynamicValue.instance,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ));
        this.getSpellAbility().addHint(hint);
    }

    private CallForthTheTempest(final CallForthTheTempest card) {
        super(card);
    }

    @Override
    public CallForthTheTempest copy() {
        return new CallForthTheTempest(this);
    }
}

enum CallForthTheTempestDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return 0;
        }

        return watcher
                .getSpellsCastThisTurn(sourceAbility.getControllerId())
                .stream()
                .filter(s -> s != null && !s.getSourceId().equals(sourceAbility.getSourceId()))
                .mapToInt(s -> s.getManaValue())
                .sum();
    }

    @Override
    public CallForthTheTempestDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "total mana value of other spells you've cast this turn";
    }
}