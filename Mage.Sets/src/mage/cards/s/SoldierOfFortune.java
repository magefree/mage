
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
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
 * @author Backfir3
 */
public final class SoldierOfFortune extends CardImpl {

    public SoldierOfFortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
		
		// {R}, {T}: Target player shuffles their library.
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShuffleLibraryTargetEffect(), new TapSourceCost());
		ability.addManaCost(new ManaCostsImpl<>("{R}"));
		ability.addTarget(new TargetPlayer());
		this.addAbility(ability);
    }

    private SoldierOfFortune(final SoldierOfFortune card) {
        super(card);
    }

    @Override
    public SoldierOfFortune copy() {
        return new SoldierOfFortune(this);
    }
}
