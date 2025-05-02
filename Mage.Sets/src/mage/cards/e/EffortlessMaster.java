package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EffortlessMaster extends CardImpl {

    public EffortlessMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // This creature enters with two +1/+1 counters on it if you've cast two or more spells this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                EffortlessMasterCondition.instance, null,
                "with two +1/+1 counters on it if you've cast two or more spells this turn"
        ));
    }

    private EffortlessMaster(final EffortlessMaster card) {
        super(card);
    }

    @Override
    public EffortlessMaster copy() {
        return new EffortlessMaster(this);
    }
}

enum EffortlessMasterCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .size() >= 2;
    }
}
