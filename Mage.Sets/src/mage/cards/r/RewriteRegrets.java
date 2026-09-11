package mage.cards.r;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;

/**
 *
 * @author muz
 */
public final class RewriteRegrets extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card with mana value 6 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 6));
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    public RewriteRegrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Return target creature or planeswalker card with mana value 6 or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Empower Jace 2.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(2).concatBy("<br>"));
    }

    private RewriteRegrets(final RewriteRegrets card) {
        super(card);
    }

    @Override
    public RewriteRegrets copy() {
        return new RewriteRegrets(this);
    }
}
