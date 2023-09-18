package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
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
public final class JacesProjection extends CardImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent(SubType.JACE);

    public JacesProjection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you draw a card, put a +1/+1 counter on Jace's Projection.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // {3}{U}: Put a loyalty counter on target Jace planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.LOYALTY.createInstance()), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private JacesProjection(final JacesProjection card) {
        super(card);
    }

    @Override
    public JacesProjection copy() {
        return new JacesProjection(this);
    }
}
