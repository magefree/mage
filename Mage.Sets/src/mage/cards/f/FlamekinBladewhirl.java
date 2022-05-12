
package mage.cards.f;

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
public final class FlamekinBladewhirl extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elemental card from your hand");
    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public FlamekinBladewhirl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast Flamekin Bladewhirl, reveal an Elemental card from your hand or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal an Elemental card from your hand or pay {3}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(3)
        ));
    }

    private FlamekinBladewhirl(final FlamekinBladewhirl card) {
        super(card);
    }

    @Override
    public FlamekinBladewhirl copy() {
        return new FlamekinBladewhirl(this);
    }
}
