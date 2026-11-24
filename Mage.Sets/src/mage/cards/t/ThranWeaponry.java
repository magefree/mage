
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class ThranWeaponry extends CardImpl {

    public ThranWeaponry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Echo {4}
        this.addAbility(new EchoAbility("{4}"));
        // You may choose not to untap Thran Weaponry during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {2}, {tap}: All creatures get +2/+2 for as long as Thran Weaponry remains tapped.
        // 611.2b: Duration.Custom makes the decorator discard the effect once the condition turns
        // false, so the boost does not come back if it is tapped again.
        Ability ability = new SimpleActivatedAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(2, 2, Duration.Custom),
                SourceTappedCondition.TAPPED,
                "All creatures get +2/+2 for as long as {this} remains tapped"
        ), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private ThranWeaponry(final ThranWeaponry card) {
        super(card);
    }

    @Override
    public ThranWeaponry copy() {
        return new ThranWeaponry(this);
    }
}
