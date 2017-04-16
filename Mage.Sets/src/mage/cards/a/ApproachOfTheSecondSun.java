package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.constants.Zone;
import mage.game.stack.Spell;

/**
 * @author stravant
 */
public class ApproachOfTheSecondSun extends CardImpl {

    public ApproachOfTheSecondSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{W}");

        getSpellAbility().addEffect(new ApproachOfTheSecondSunEffect());
        getSpellAbility().addWatcher(new ApproachOfTheSecondSunWatcher());
    }

    public ApproachOfTheSecondSun(final ApproachOfTheSecondSun card) {
        super(card);
    }

    @Override
    public ApproachOfTheSecondSun copy() {
        return new ApproachOfTheSecondSun(this);
    }
}

class ApproachOfTheSecondSunEffect extends OneShotEffect {
    public ApproachOfTheSecondSunEffect() {
        super(Outcome.Win);
        this.staticText =
                "If Approach of the Second Sun was cast from your hand and you've cast another spell named Approach of the Second Sun this game, you win the game. "
                + "Otherwise, put Approach of the Second Sun into its owner's library seventh from the top and you gain 7 life.";
    }

    public ApproachOfTheSecondSunEffect(final ApproachOfTheSecondSunEffect effect) {
        super(effect);
    }

    @Override
    public ApproachOfTheSecondSunEffect copy() {
        return new ApproachOfTheSecondSunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ApproachOfTheSecondSunWatcher watcher =
                    (ApproachOfTheSecondSunWatcher) game.getState().getWatchers().get("approachOfTheSecondSunWatcher", source.getControllerId());
            if (watcher != null && watcher.getApproachesCast() > 1) {
                // Win the game
                controller.won(game);
            } else {
                // Gain 7 life and put this back into library.
                controller.gainLife(7, game);

                // Put this into the library as the 7th from the top
                Spell spell = game.getStack().getSpell(source.getSourceId());
                if (spell != null) {
                    Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                    if (spellCard != null) {
                        List<Card> top6 = new ArrayList<>();
                        // Cut the top 6 cards off into a temporary array
                        for (int i = 0; i < 6 && controller.getLibrary().hasCards(); ++i) {
                            top6.add(controller.getLibrary().removeFromTop(game));
                        }
                        // Put this card (if the ability came from an ApproachOfTheSecondSun spell card) on top
                        controller.moveCardToLibraryWithInfo(spellCard, source.getSourceId(), game, Zone.STACK, true, true);

                        // put the top 6 we took earlier back on top (going in reverse order this time to get them back
                        // on top in the proper order)
                        for (int i = top6.size() - 1; i >= 0; --i) {
                            controller.getLibrary().putOnTop(top6.get(i), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}


class ApproachOfTheSecondSunWatcher extends Watcher {
    private int approachesCast = 0;

    public ApproachOfTheSecondSunWatcher() {
        super("approachOfTheSecondSunWatcher", WatcherScope.PLAYER);
    }

    public ApproachOfTheSecondSunWatcher(final ApproachOfTheSecondSunWatcher watcher) {
        super(watcher);
        approachesCast = watcher.approachesCast;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
            ++approachesCast;
        }
    }

    public int getApproachesCast() {
        return approachesCast;
    }

    @Override
    public ApproachOfTheSecondSunWatcher copy() {
        return new ApproachOfTheSecondSunWatcher(this);
    }
}
