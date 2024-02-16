
package mage.cards.d;

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
 * @author Will
 */
public final class DaringBuccaneer extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Pirate card from your hand");
    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public DaringBuccaneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast Daring Buccaneer, reveal a Pirate card from your hand or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal a Pirate card from your hand or pay {2}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(2)
        ));

    }

    private DaringBuccaneer(final DaringBuccaneer card) {
        super(card);
    }

    @Override
    public DaringBuccaneer copy() {
        return new DaringBuccaneer(this);
    }
}
