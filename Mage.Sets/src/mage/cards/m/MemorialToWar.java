

package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetLandPermanent;

/**
 * @author Rystan
 */
public final class MemorialToWar extends CardImpl {

    public MemorialToWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        // Memorial to War enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        this.addAbility(new RedManaAbility());
        
        // {4}{R}, {T}, Sacrifice Memorial to War: Destroy target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{4}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

    }

    private MemorialToWar(final MemorialToWar card) {
        super(card);
    }

    @Override
    public MemorialToWar copy() {
        return new MemorialToWar(this);
    }

}
