package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForceOfVigor extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a green card from your hand");
    private static final FilterPermanent filter2
            = new FilterArtifactOrEnchantmentPermanent("artifacts and/or enchantments");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public ForceOfVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{G}");

        // If it's not your turn, you may exile a green card from your hand rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new ExileFromHandCost(new TargetCardInHand(filter)), NotMyTurnCondition.instance,
                "If it's not your turn, you may exile a green card from " +
                        "your hand rather than pay this spell's mana cost."
        ).addHint(NotMyTurnHint.instance));

        // Destroy up to two target artifacts and/or enchantments.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, filter2, false));
    }

    private ForceOfVigor(final ForceOfVigor card) {
        super(card);
    }

    @Override
    public ForceOfVigor copy() {
        return new ForceOfVigor(this);
    }
}
