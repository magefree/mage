
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author Quercitron
 */
public final class StormEntity extends CardImpl {

    public StormEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Storm Entity enters the battlefield with a +1/+1 counter on it for each other spell cast this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        new OtherSpellsCastThisTurnCount(),
                        true),
                "with a +1/+1 counter on it for each other spell cast this turn"));
    }

    private StormEntity(final StormEntity card) {
        super(card);
    }

    @Override
    public StormEntity copy() {
        return new StormEntity(this);
    }
}

class OtherSpellsCastThisTurnCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if(watcher != null ) {
            return watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn() - 1;
        }
        return 0;
    }

    @Override
    public OtherSpellsCastThisTurnCount copy() {
        return new OtherSpellsCastThisTurnCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "other spell cast this turn";
    }

}
