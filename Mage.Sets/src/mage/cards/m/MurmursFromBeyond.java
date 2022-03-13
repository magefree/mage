
package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MurmursFromBeyond extends CardImpl {

    public MurmursFromBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");
        this.subtype.add(SubType.ARCANE);

        // Reveal the top three cards of your library. An opponent chooses one of them. Put that card into your graveyard and the rest into your hand.
        this.getSpellAbility().addEffect(new MurmursFromBeyondEffect());
    }

    private MurmursFromBeyond(final MurmursFromBeyond card) {
        super(card);
    }

    @Override
    public MurmursFromBeyond copy() {
        return new MurmursFromBeyond(this);
    }
}

class MurmursFromBeyondEffect extends OneShotEffect {

    public MurmursFromBeyondEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top three cards of your library. An opponent chooses one of them. Put that card into your graveyard and the rest into your hand";
    }

    public MurmursFromBeyondEffect(final MurmursFromBeyondEffect effect) {
        super(effect);
    }

    @Override
    public MurmursFromBeyondEffect copy() {
        return new MurmursFromBeyondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.revealCards(staticText, cards, game);
                Card cardToGraveyard;
                if (cards.size() == 1) {
                    cardToGraveyard = cards.getRandom(game);
                } else {
                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target target = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, target, source, game);
                        opponent = game.getPlayer(target.getFirstTarget());
                    }
                    TargetCard target = new TargetCard(1, Zone.LIBRARY, new FilterCard());
                    opponent.chooseTarget(outcome, cards, target, source, game);
                    cardToGraveyard = game.getCard(target.getFirstTarget());
                }
                if (cardToGraveyard != null) {
                    controller.moveCards(cardToGraveyard, Zone.GRAVEYARD, source, game);
                    cards.remove(cardToGraveyard);
                }
                controller.moveCards(cards, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
