package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NinjaOfTheHand extends CardImpl {

    public NinjaOfTheHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Power-up -- {4}{B}: Each opponent discards a card. Put a +1/+1 counter on this creature.
        Ability ability = new PowerUpAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), new ManaCostsImpl<>("{4}{B}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private NinjaOfTheHand(final NinjaOfTheHand card) {
        super(card);
    }

    @Override
    public NinjaOfTheHand copy() {
        return new NinjaOfTheHand(this);
    }
}
