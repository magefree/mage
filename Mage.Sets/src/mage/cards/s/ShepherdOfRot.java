
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ShepherdOfRot extends CardImpl {
    
    static final String rule = "Each player loses 1 life for each Zombie on the battlefield";
    
    static final private FilterPermanent filter = new FilterPermanent("Zombie");
    
    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public ShepherdOfRot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Each player loses 1 life for each Zombie on the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeAllPlayersEffect(new PermanentsOnBattlefieldCount(filter), rule), new TapSourceCost()));
    }

    private ShepherdOfRot(final ShepherdOfRot card) {
        super(card);
    }

    @Override
    public ShepherdOfRot copy() {
        return new ShepherdOfRot(this);
    }
}
