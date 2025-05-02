package mage.cards.b;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BecomeTheAvalanche extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures you control with power 4 or greater", xValue);

    public BecomeTheAvalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Draw a card for each creature you control with power 4 or greater. Then creatures you control get +X/+X until end of turn, where X is the number of cards in your hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue));
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                CardsInControllerHandCount.ANY, CardsInControllerHandCount.ANY, Duration.EndOfTurn
        ).setText("Then creatures you control get +X/+X until end of turn, where X is the number of cards in your hand"));
        this.getSpellAbility().addHint(hint);
    }

    private BecomeTheAvalanche(final BecomeTheAvalanche card) {
        super(card);
    }

    @Override
    public BecomeTheAvalanche copy() {
        return new BecomeTheAvalanche(this);
    }
}
