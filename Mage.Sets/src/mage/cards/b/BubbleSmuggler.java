package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author notgreat
 */
public final class BubbleSmuggler extends CardImpl {

    public BubbleSmuggler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Disguise {5}{U}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{5}{U}")));

        // As Bubble Smuggler is turned face up, put four +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(4));
        effect.setText("put four +1/+1 counters on it");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AsTurnedFaceUpEffect(effect, false));
        ability.setWorksFaceDown(true);
        this.addAbility(ability);
    }

    private BubbleSmuggler(final BubbleSmuggler card) {
        super(card);
    }

    @Override
    public BubbleSmuggler copy() {
        return new BubbleSmuggler(this);
    }
}
