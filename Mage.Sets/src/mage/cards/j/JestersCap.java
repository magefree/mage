
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class JestersCap extends CardImpl {

    public JestersCap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}, Sacrifice Jester's Cap: Search target player's library for three cards and exile them. Then that player shuffles their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JestersCapEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private JestersCap(final JestersCap card) {
        super(card);
    }

    @Override
    public JestersCap copy() {
        return new JestersCap(this);
    }
}

class JestersCapEffect extends OneShotEffect {

    public JestersCapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target player's library for three cards and exile them. Then that player shuffles";
    }

    public JestersCapEffect(final JestersCapEffect effect) {
        super(effect);
    }

    @Override
    public JestersCapEffect copy() {
        return new JestersCapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(3, 3, new FilterCard());
            player.searchLibrary(target, source, game, targetPlayer.getId());
            for (UUID cardId : target.getTargets()) {
                final Card targetCard = game.getCard(cardId);
                if (targetCard != null) {
                    applied |= player.moveCardToExileWithInfo(targetCard, null, null, source, game, Zone.LIBRARY, true);
                }
            }
            targetPlayer.shuffleLibrary(source, game);
        }
        return applied;
    }
}