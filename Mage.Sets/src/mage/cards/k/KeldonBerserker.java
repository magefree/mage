
package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeldonBerserker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("you control no untapped lands");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public KeldonBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Keldon Berserker attacks, if you control no untapped lands, it gets +3/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                3, 0, Duration.EndOfTurn, "it"
        )).withInterveningIf(condition));
    }

    private KeldonBerserker(final KeldonBerserker card) {
        super(card);
    }

    @Override
    public KeldonBerserker copy() {
        return new KeldonBerserker(this);
    }
}
