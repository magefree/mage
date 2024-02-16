
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class GoldmeadowStalwart extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Kithkin card from your hand");
    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public GoldmeadowStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast Goldmeadow Stalwart, reveal a Kithkin card from your hand or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal a Kithkin card from your hand or pay {3}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(3)
        ));
    }

    private GoldmeadowStalwart(final GoldmeadowStalwart card) {
        super(card);
    }

    @Override
    public GoldmeadowStalwart copy() {
        return new GoldmeadowStalwart(this);
    }
}
