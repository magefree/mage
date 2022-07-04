
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author L_J
 */
public final class FrontlineStrategist extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Soldier creatures");

    static {
        filter.add(Predicates.not(SubType.SOLDIER.getPredicate()));
    }

    public FrontlineStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{W}")));
        // When Frontline Strategist is turned face up, prevent all combat damage non-Soldier creatures would deal this turn.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true).setText("prevent all combat damage non-Soldier creatures would deal this turn")));
    }

    private FrontlineStrategist(final FrontlineStrategist card) {
        super(card);
    }

    @Override
    public FrontlineStrategist copy() {
        return new FrontlineStrategist(this);
    }
}
