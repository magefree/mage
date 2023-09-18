
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth

 */
public final class GrazingKelpie extends CardImpl {

    public GrazingKelpie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G/U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {GU}, Sacrifice Grazing Kelpie: Put target card from a graveyard on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false), new ManaCostsImpl<>("{G/U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
        
        // Persist
        this.addAbility(new PersistAbility());
    }

    private GrazingKelpie(final GrazingKelpie card) {
        super(card);
    }

    @Override
    public GrazingKelpie copy() {
        return new GrazingKelpie(this);
    }
}
