
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReplaceOpponentCardsInHandWithSelectedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Eirkei
 */
public final class JestersMask extends CardImpl {

    public JestersMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Jester's Mask enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {1}, {tap}, Sacrifice Jester's Mask: Target opponent puts the cards from their hand on top of their library. Search that player's library for that many cards. That player puts those cards into their hand, then shuffles their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReplaceOpponentCardsInHandWithSelectedEffect(), new ManaCostsImpl<>("{1}"));       
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JestersMask(final JestersMask card) {
        super(card);
    }

    @Override
    public JestersMask copy() {
        return new JestersMask(this);
    }
}
