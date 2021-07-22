
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author TheElk801
 */
public final class KeldonBerserker extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("untapped lands");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public KeldonBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Keldon Berserker attacks, if you control no untapped lands, it gets +3/+0 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), false),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "Whenever {this} attacks, if you control no untapped lands, it gets +3/+0 until end of turn."
        ));
    }

    private KeldonBerserker(final KeldonBerserker card) {
        super(card);
    }

    @Override
    public KeldonBerserker copy() {
        return new KeldonBerserker(this);
    }
}
