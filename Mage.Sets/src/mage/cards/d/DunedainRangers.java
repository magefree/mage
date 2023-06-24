package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.RingBearerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DunedainRangers extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(RingBearerPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter, ComparisonType.EQUAL_TO, 0
    );

    public DunedainRangers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land enters the battlefield under your control, if you don't control a Ring-bearer, the Ring tempts you.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new LandfallAbility(new TheRingTemptsYouEffect()),
                condition, "Whenever a land enters the battlefield under your control, " +
                "if you don't control a Ring-bearer, the Ring tempts you."
        ).setAbilityWord(AbilityWord.LANDFALL));
    }

    private DunedainRangers(final DunedainRangers card) {
        super(card);
    }

    @Override
    public DunedainRangers copy() {
        return new DunedainRangers(this);
    }
}
