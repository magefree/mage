

package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author Rystan
 */
public final class MemorialToGenius extends CardImpl {

    public MemorialToGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        // Memorial to Genius enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        this.addAbility(new BlueManaAbility());
        
        // {4}{U}, {T}, Sacrifice Memorial to Genius: Draw two cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{4}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private MemorialToGenius(final MemorialToGenius card) {
        super(card);
    }

    @Override
    public MemorialToGenius copy() {
        return new MemorialToGenius(this);
    }

}
