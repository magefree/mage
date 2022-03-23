
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public final class WandOfDenial extends CardImpl {

    public WandOfDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}: Look at the top card of target player's library. If it's a nonland card, you may pay 2 life. If you do, put it into that player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WandOfDenialEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private WandOfDenial(final WandOfDenial card) {
        super(card);
    }

    @Override
    public WandOfDenial copy() {
        return new WandOfDenial(this);
    }
}

class WandOfDenialEffect extends OneShotEffect {

    public WandOfDenialEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top card of target player's library. If it's a nonland card, you may pay 2 life. If you do, put it into that player's graveyard.";
    }
    
    public WandOfDenialEffect(final WandOfDenialEffect effect) {
        super(effect);
    }

    @Override
    public WandOfDenialEffect copy() {
        return new WandOfDenialEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null && targetPlayer != null) {
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if (card != null) {
                MageObject sourceObject = game.getObject(source);
                controller.lookAtCards(sourceObject != null ? sourceObject.getName() : "", new CardsImpl(card), game);
                if (!card.isLand(game)
                        && controller.canPayLifeCost(source)
                        && controller.getLife() >= 2
                        && controller.chooseUse(Outcome.Neutral, "Pay 2 life to put " + card.getLogName() + " into graveyard?", source, game)) {
                    controller.loseLife(2, game, source, false);
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
                return true;
            }
        }
        return false;
    }
    
}
