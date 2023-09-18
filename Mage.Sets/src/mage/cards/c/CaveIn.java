package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class CaveIn extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a red card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public CaveIn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // You may exile a red card from your hand rather than pay Cave-In's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));

        // Cave-In deals 2 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(2));
    }

    private CaveIn(final CaveIn card) {
        super(card);
    }

    @Override
    public CaveIn copy() {
        return new CaveIn(this);
    }
}
