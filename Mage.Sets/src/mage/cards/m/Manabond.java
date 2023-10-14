
package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class Manabond extends CardImpl {

    public Manabond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // At the beginning of your end step, reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new ManabondEffect(), true));
    }

    private Manabond(final Manabond card) {
        super(card);
    }

    @Override
    public Manabond copy() {
        return new Manabond(this);
    }
}

class ManabondEffect extends OneShotEffect {

    public ManabondEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand";
    }

    private ManabondEffect(final ManabondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            controller.revealCards(sourceObject.getIdName(), controller.getHand(), game);
            Set<Card> toBattlefield = new LinkedHashSet<>();
            for (UUID uuid : controller.getHand()) {
                Card card = game.getCard(uuid);
                if (card != null && card.isLand(game)) {
                    toBattlefield.add(card);
                }

            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
            controller.discard(controller.getHand().size(), false, false, source, game);
            return true;
        }
        return false;
    }

    @Override
    public ManabondEffect copy() {
        return new ManabondEffect(this);
    }
}
