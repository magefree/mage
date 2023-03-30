
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllThatAttackedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class FuryOfTheHorde extends CardImpl {

    private static final FilterCard filter = new FilterCard("red cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public FuryOfTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");

        // You may exile two red cards from your hand rather than pay Fury of the Horde's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));

        // Untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addEffect(new UntapAllThatAttackedEffect());
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());

    }

    private FuryOfTheHorde(final FuryOfTheHorde card) {
        super(card);
    }

    @Override
    public FuryOfTheHorde copy() {
        return new FuryOfTheHorde(this);
    }
}
