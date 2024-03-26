
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Backfir3
 */
public final class GoblinTurncoat extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOBLIN, "a Goblin");

    public GoblinTurncoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

		// Sacrifice a Goblin: Regenerate Goblin Turncoat.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),
			new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private GoblinTurncoat(final GoblinTurncoat card) {
        super(card);
    }

    @Override
    public GoblinTurncoat copy() {
        return new GoblinTurncoat(this);
    }
}
