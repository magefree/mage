package mage.cards.a;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 * @author stravant
 */
public final class ApproachOfTheSecondSun extends CardImpl {

    public ApproachOfTheSecondSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}");

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
        this.staticText
                = "If this spell was cast from your hand and you've cast another spell named Approach of the Second Sun this game, you win the game. "
                + "Otherwise, put {this} into its owner's library seventh from the top and you gain 7 life.";
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
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (controller != null && spell != null) {
            ApproachOfTheSecondSunWatcher watcher
                    = (ApproachOfTheSecondSunWatcher) game.getState().getWatchers().get(ApproachOfTheSecondSunWatcher.class.getSimpleName());
            if (watcher != null
                    && !spell.isCopiedSpell()
                    && watcher.getApproachesCast(controller.getId()) > 1
                    && spell.getFromZone() == Zone.HAND) {
                // Win the game
                controller.won(game);
            } else {
                // Gain 7 life and put this back into library.
                controller.gainLife(7, game, source);

                // Put this into the library as the 7th from the top
                if (spell.isCopiedSpell()) {
                    return true;
                }
                Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                if (spellCard != null) {
                    controller.putCardOnTopXOfLibrary(spellCard, game, source, 7);
                }
            }
            return true;
        }
        return false;
    }
}

class ApproachOfTheSecondSunWatcher extends Watcher {

    private Map<UUID, Integer> approachesCast = new HashMap<>();

    public ApproachOfTheSecondSunWatcher() {
        super(ApproachOfTheSecondSunWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ApproachOfTheSecondSunWatcher(final ApproachOfTheSecondSunWatcher watcher) {
        super(watcher);
        approachesCast = new HashMap<>(watcher.approachesCast);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getSourceId());
            if (spell != null && spell.getName().equals("Approach of the Second Sun")) {
                approachesCast.put(event.getPlayerId(), getApproachesCast(event.getPlayerId()) + 1);
            }
        }
    }

    public int getApproachesCast(UUID player) {
        return approachesCast.getOrDefault(player, 0);
    }

    @Override
    public ApproachOfTheSecondSunWatcher copy() {
        return new ApproachOfTheSecondSunWatcher(this);
    }
}
