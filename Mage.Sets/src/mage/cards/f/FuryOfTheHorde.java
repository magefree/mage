
package mage.cards.f;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class FuryOfTheHorde extends CardImpl {

    private static final FilterCard filter = new FilterCard("red cards");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures that attacked this turn");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(AttackedThisTurnPredicate.instance);
    }

    public FuryOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // You may exile two red cards from your hand rather than pay Fury of the Horde's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));

        // Untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addEffect(new UntapAllEffect(filter2));
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());
    }

    private FuryOfTheHorde(final FuryOfTheHorde card) {
        super(card);
    }

    @Override
    public FuryOfTheHorde copy() {
        return new FuryOfTheHorde(this);
    }
}
