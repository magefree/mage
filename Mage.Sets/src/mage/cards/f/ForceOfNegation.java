package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForceOfNegation extends CardImpl {

    private static final FilterOwnedCard filter = new FilterOwnedCard("a blue card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ForceOfNegation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // If it's not your turn, you may exile a blue card from your hand rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new ExileFromHandCost(new TargetCardInHand(filter)), NotMyTurnCondition.instance,
                "If it's not your turn, you may exile a blue card from " +
                        "your hand rather than pay this spell's mana cost."
        ).addHint(NotMyTurnHint.instance));

        // Counter target noncreature spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private ForceOfNegation(final ForceOfNegation card) {
        super(card);
    }

    @Override
    public ForceOfNegation copy() {
        return new ForceOfNegation(this);
    }
}
