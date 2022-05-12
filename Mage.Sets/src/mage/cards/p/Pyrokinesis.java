package mage.cards.p;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Pyrokinesis extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a red card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public Pyrokinesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");

        // You may exile a red card from your hand rather than pay Pyrokinesis's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));

        // Pyrokinesis deals 4 damage divided as you choose among any number of target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(4));
    }

    private Pyrokinesis(final Pyrokinesis card) {
        super(card);
    }

    @Override
    public Pyrokinesis copy() {
        return new Pyrokinesis(this);
    }
}
