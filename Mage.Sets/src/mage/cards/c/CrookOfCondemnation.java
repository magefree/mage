
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class CrookOfCondemnation extends CardImpl {
    
    private UUID exileId = UUID.randomUUID();

    public CrookOfCondemnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        

        // {1}, {t}: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
        
        // {1}, Exile Crook of Condemnation: Exile all cards from all graveyards.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileGraveyardAllPlayersEffect(), new ManaCostsImpl("{1}"));
        ability2.addCost(new ExileSourceCost());
        this.addAbility(ability2);
        
    }

    public CrookOfCondemnation(final CrookOfCondemnation card) {
        super(card);
    }

    @Override
    public CrookOfCondemnation copy() {
        return new CrookOfCondemnation(this);
    }
}
