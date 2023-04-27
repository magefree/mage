
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ShuffleLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Wehk
 */
public final class BoggartForager extends CardImpl {

    public BoggartForager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN, SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice Boggart Forager: Target player shuffles their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShuffleLibraryTargetEffect(), new ManaCostsImpl<>("{R}"));
	ability.addCost(new SacrificeSourceCost());
	ability.addTarget(new TargetPlayer());
	this.addAbility(ability);
    }
    
    private BoggartForager(final BoggartForager card) {
        super(card);
    }

    @Override
    public BoggartForager copy() {
        return new BoggartForager(this);
    }
}
