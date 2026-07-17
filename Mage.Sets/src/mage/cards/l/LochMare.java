package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LochMare extends CardImpl {

    public LochMare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // This creature enters with three -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(3)));

        // {1}{U}, Remove a counter from this creature: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new RemoveCountersSourceCost(1));
        this.addAbility(ability);

        // {2}{U}, Remove two counters from this creature: Tap target creature. Put a stun counter on it.
        ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new RemoveCountersSourceCost(2));
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).withTargetDescription("it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LochMare(final LochMare card) {
        super(card);
    }

    @Override
    public LochMare copy() {
        return new LochMare(this);
    }
}
