
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileTopCreatureCardOfGraveyardCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ZombieScavengers extends CardImpl {

    public ZombieScavengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Exile the top creature card of your graveyard: Regenerate Zombie Scavengers.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ExileTopCreatureCardOfGraveyardCost(1)));
    }

    private ZombieScavengers(final ZombieScavengers card) {
        super(card);
    }

    @Override
    public ZombieScavengers copy() {
        return new ZombieScavengers(this);
    }
}
