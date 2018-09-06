package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleGraveyardSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jmharmon
 */

import java.util.UUID;

public class SearchForSurvivors extends CardImpl {

    public SearchForSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Reorder your graveyard at random. (This is oracle text for shuffling your graveyard.)
        this.getSpellAbility().addEffect(new ShuffleGraveyardSourceEffect());

        // An opponent chooses a card at random in your graveyard. If it’s a creature card, put it onto the battlefield. Otherwise, exile it.
        this.getSpellAbility().addEffect(new SearchForSurvivorsEffect());
    }

    public SearchForSurvivors(final SearchForSurvivors card) {
        super(card);
    }

    @Override
    public SearchForSurvivors copy() {
        return new SearchForSurvivors(this);
    }
}

class SearchForSurvivorsEffect extends OneShotEffect {

    public SearchForSurvivorsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "An opponent chooses a card at random in your graveyard. If it’s a creature card, put it onto the battlefield. Otherwise, exile it.";
    }

    public SearchForSurvivorsEffect(final SearchForSurvivorsEffect effect) {
        super(effect);
    }

    @Override
    public SearchForSurvivorsEffect copy() {
        return new SearchForSurvivorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getSourceId());
        if (player != null && !player.getGraveyard().isEmpty()) {
            Card card = player.getGraveyard().getRandom(game);
            if (card != null) {
                Zone targetZone = Zone.EXILED;
                String text = " moved to exile of ";
                if (card.isCreature()) {
                    targetZone = Zone.BATTLEFIELD;
                    text = " put onto battlefield for ";
                }
                card.moveToZone(targetZone, source.getSourceId(), game, false);
                game.informPlayers(new StringBuilder("Search for Survivors: ").append(card.getName()).append(text).append(player.getLogName()).toString());
                return true;
            }
        }
        return true;
    }
}
