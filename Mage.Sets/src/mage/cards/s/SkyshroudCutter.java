
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.GainLifePlayersCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class SkyshroudCutter extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("If you control a Forest");
    
    static {
        filter.add(SubType.FOREST.getPredicate());
    }
    
    public SkyshroudCutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If you control a Forest, rather than pay Skyshroud Cutter's mana cost, you may have each other player gain 5 life.
        this.addAbility(new AlternativeCostSourceAbility(new GainLifePlayersCost(5), new PermanentsOnTheBattlefieldCondition(filter)));
    }

    private SkyshroudCutter(final SkyshroudCutter card) {
        super(card);
    }

    @Override
    public SkyshroudCutter copy() {
        return new SkyshroudCutter(this);
    }
}
