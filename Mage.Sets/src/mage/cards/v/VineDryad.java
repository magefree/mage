package mage.cards.v;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VineDryad extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a green card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public VineDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Forestwalk
        this.addAbility(new ForestwalkAbility());

        // You may exile a green card from your hand rather than pay Vine Dryad's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));
    }

    private VineDryad(final VineDryad card) {
        super(card);
    }

    @Override
    public VineDryad copy() {
        return new VineDryad(this);
    }
}
