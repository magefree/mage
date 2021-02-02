
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class GoblinMasons extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Wall");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    
    public GoblinMasons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //When Goblin Masons dies, destroy target Wall
        DiesSourceTriggeredAbility ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    
    }

    private GoblinMasons(final GoblinMasons card) {
        super(card);
    }

    @Override
    public GoblinMasons copy() {
        return new GoblinMasons(this);
    }
}
