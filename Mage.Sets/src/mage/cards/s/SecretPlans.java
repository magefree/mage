package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SecretPlans extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Face-down creatures");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public SecretPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}");

        // Face-down creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                0, 1, Duration.WhileOnBattlefield, filter
        )));

        // Whenever a permanent you control is turned face up, draw a card.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_PERMANENT
        ));
    }

    private SecretPlans(final SecretPlans card) {
        super(card);
    }

    @Override
    public SecretPlans copy() {
        return new SecretPlans(this);
    }
}
