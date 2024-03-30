package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazzleDazzler extends CardImpl {

    public RazzleDazzler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you cast your second spell each turn, put a +1/+1 counter on Razzle-Dazzler. It can't be blocked this turn.
        Ability ability = new CastSecondSpellTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("it can't be blocked this turn"));
        this.addAbility(ability);
    }

    private RazzleDazzler(final RazzleDazzler card) {
        super(card);
    }

    @Override
    public RazzleDazzler copy() {
        return new RazzleDazzler(this);
    }
}
