package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonsCompany extends CardImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent(SubType.GIDEON);

    public GideonsCompany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you gain life, put two +1/+1 counters on Gideon's Company.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false
        ));

        // {3}{W}: Put a loyalty counter on target Gideon planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.LOYALTY.createInstance()), new ManaCostsImpl<>("{3}{W}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GideonsCompany(final GideonsCompany card) {
        super(card);
    }

    @Override
    public GideonsCompany copy() {
        return new GideonsCompany(this);
    }
}
