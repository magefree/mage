package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForceOfDespair extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a black card from your hand");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("creatures that entered the battlefield this turn");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter2.add(EnteredThisTurnPredicate.instance);
    }

    public ForceOfDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // If it's not your turn, you may exile a black card from your hand rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new ExileFromHandCost(new TargetCardInHand(filter)), NotMyTurnCondition.instance,
                "If it's not your turn, you may exile a black card from " +
                        "your hand rather than pay this spell's mana cost."
        ).addHint(NotMyTurnHint.instance));

        // Destroy all creatures that entered the battlefield this turn.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter2));
    }

    private ForceOfDespair(final ForceOfDespair card) {
        super(card);
    }

    @Override
    public ForceOfDespair copy() {
        return new ForceOfDespair(this);
    }
}
