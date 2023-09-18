
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class FoulFamiliar extends CardImpl {

    public FoulFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Foul Familiar can't block.
        this.addAbility(new CantBlockAbility());
        // {B}, Pay 1 life: Return Foul Familiar to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{B}"));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private FoulFamiliar(final FoulFamiliar card) {
        super(card);
    }

    @Override
    public FoulFamiliar copy() {
        return new FoulFamiliar(this);
    }
}
