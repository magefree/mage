package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForceOfVirtue extends CardImpl {

    private static final FilterOwnedCard filter = new FilterOwnedCard("a white card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ForceOfVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // If it's not your turn, you may exile a white card from your hand rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new ExileFromHandCost(new TargetCardInHand(filter)), NotMyTurnCondition.instance,
                "If it's not your turn, you may exile a white card from " +
                        "your hand rather than pay this spell's mana cost."
        ).addHint(NotMyTurnHint.instance));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)
        ));
    }

    private ForceOfVirtue(final ForceOfVirtue card) {
        super(card);
    }

    @Override
    public ForceOfVirtue copy() {
        return new ForceOfVirtue(this);
    }
}
