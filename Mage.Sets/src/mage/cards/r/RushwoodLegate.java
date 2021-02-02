
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class RushwoodLegate extends CardImpl {

    private static final FilterPermanent filterIsland = new FilterPermanent();
    private static final FilterPermanent filterForest = new FilterPermanent();

    static {
        filterForest.add(SubType.FOREST.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
    }

    public RushwoodLegate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If an opponent controls an Island and you control a Forest, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls an Island and you control a Forest",
                new OpponentControlsPermanentCondition(filterIsland),
                new PermanentsOnTheBattlefieldCondition(filterForest));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
    }

    private RushwoodLegate(final RushwoodLegate card) {
        super(card);
    }

    @Override
    public RushwoodLegate copy() {
        return new RushwoodLegate(this);
    }
}
