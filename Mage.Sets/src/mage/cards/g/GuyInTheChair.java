package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuyInTheChair extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIDER, "Spider");

    public GuyInTheChair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Web Support -- {2}{G}, {T}: Put a +1/+1 counter on target Spider. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Web Support"));
    }

    private GuyInTheChair(final GuyInTheChair card) {
        super(card);
    }

    @Override
    public GuyInTheChair copy() {
        return new GuyInTheChair(this);
    }
}
