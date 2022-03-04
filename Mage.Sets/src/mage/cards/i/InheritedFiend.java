package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InheritedFiend extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public InheritedFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{B}: Exile target creature card from a graveyard. Put a +1/+1 counter on Inherited Fiend.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("."));
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private InheritedFiend(final InheritedFiend card) {
        super(card);
    }

    @Override
    public InheritedFiend copy() {
        return new InheritedFiend(this);
    }
}
