

package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.SoldierToken;

/**
 * @author Rystan
 */
public final class MemorialToGlory extends CardImpl {

    public MemorialToGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        // Memorial to Glory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        this.addAbility(new WhiteManaAbility());
        
        // {3}{W}, {T}, Sacrifice Memorial to Glory: Creature two 1/1 white Soldier creature tokens.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierToken(), 2), new ManaCostsImpl<>("{3}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private MemorialToGlory(final MemorialToGlory card) {
        super(card);
    }

    @Override
    public MemorialToGlory copy() {
        return new MemorialToGlory(this);
    }

}
