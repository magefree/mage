package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NarnamRenegade extends CardImpl {

    public NarnamRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // <i>Revolt</i> &mdash; Narnam Renegade enters the battlefield with a +1/+1 counter on it if a permanent you controlled left this battlefield this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                RevoltCondition.instance, "<i>Revolt</i> &mdash; {this} enters the battlefield with " +
                "a +1/+1 counter on it if a permanent you controlled left the battlefield this turn.", null
        ), new RevoltWatcher());
    }

    private NarnamRenegade(final NarnamRenegade card) {
        super(card);
    }

    @Override
    public NarnamRenegade copy() {
        return new NarnamRenegade(this);
    }
}
