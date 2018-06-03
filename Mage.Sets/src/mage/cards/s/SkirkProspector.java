
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SkirkProspector extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Goblin");

    static {
        filter.add(new SubtypePredicate((SubType.GOBLIN)));
    }

    public SkirkProspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice a Goblin: Add {R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,filter,true))));
    }

    public SkirkProspector(final SkirkProspector card) {
        super(card);
    }

    @Override
    public SkirkProspector copy() {
        return new SkirkProspector(this);
    }
}
