
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class GoblinMasons extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Wall");

    static {
        filter.add(new SubtypePredicate(SubType.WALL));
    }

    
    public GoblinMasons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //When Goblin Masons dies, destroy target Wall
        DiesTriggeredAbility ability = new DiesTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    
    }

    public GoblinMasons(final GoblinMasons card) {
        super(card);
    }

    @Override
    public GoblinMasons copy() {
        return new GoblinMasons(this);
    }
}
