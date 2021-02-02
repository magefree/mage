
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Wehk
 */
public final class Sewerdreg extends CardImpl {

    public Sewerdreg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
        
        // Sacrifice Sewerdreg: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new SacrificeSourceCost());
	ability.addTarget(new TargetCardInGraveyard());
	this.addAbility(ability);
    }

    private Sewerdreg(final Sewerdreg card) {
        super(card);
    }

    @Override
    public Sewerdreg copy() {
        return new Sewerdreg(this);
    }
}
