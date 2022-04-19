
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.FearAbility;
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
public final class SqueakingPieSneak extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Goblin card from your hand");
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public SqueakingPieSneak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As an additional cost to cast Squeaking Pie Sneak, reveal a Goblin card from your hand or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal a Goblin card from your hand or pay {3}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(3)
        ));
        // Fear
        this.addAbility(FearAbility.getInstance());
    }

    private SqueakingPieSneak(final SqueakingPieSneak card) {
        super(card);
    }

    @Override
    public SqueakingPieSneak copy() {
        return new SqueakingPieSneak(this);
    }
}
