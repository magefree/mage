package mage.cards.a;

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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author stravant
 */
public final class ApproachOfTheSecondSun extends CardImpl {

    public ApproachOfTheSecondSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}");

        // If this spell was cast from your hand and you've cast another spell named Approach of the Second Sun this game,
        // you win the game. Otherwise, put Approach of the Second Sun into its owner's library seventh from the top and you gain 7 life.        
        getSpellAbility().addEffect(new ApproachOfTheSecondSunEffect());
        getSpellAbility().addWatcher(new ApproachOfTheSecondSunWatcher());
    }

    private ApproachOfTheSecondSun(final ApproachOfTheSecondSun card) {
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
                = "If this spell was cast from your hand and you've cast another spell named {this} this game, you win the game. "
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
        ApproachOfTheSecondSunWatcher watcher
                = game.getState().getWatcher(ApproachOfTheSecondSunWatcher.class);
        if (controller == null || spell == null || watcher == null) {
            return false;
        }
        //If this spell was cast from your hand and you've cast another spell named {this} this game
        //A copy of a spell isn’t cast, so it won’t count as the first nor as the second Approach of the Second Sun. (2017-04-18)
        if (!spell.isCopy() //TODO: copied spells should not be "from" hand
                && spell.getFromZone() == Zone.HAND
                && watcher.getApproachesCast(controller.getId()) > 1) {
            // Win the game
            controller.won(game);
        } else {
            // Gain 7 life and put this back into library.
            controller.gainLife(7, game, source);

            // Put this into the library as the 7th from the top
            Card spellCard = spell.getCard();
            if (spellCard != null) { //No need to check copies, copies put to library cease to exist as state based action
                controller.putCardOnTopXOfLibrary(spellCard, game, source, 7, true);
            }
        }
        return true;
    }
}

class ApproachOfTheSecondSunWatcher extends Watcher {

    private final Map<UUID, Integer> approachesCast = new HashMap<>();

    public ApproachOfTheSecondSunWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) { //A copy of a spell isn’t cast, so it won’t count as the first nor as the second Approach of the Second Sun. (2017-04-18)
            Spell spell = game.getStack().getSpell(event.getSourceId());
            if (spell != null && spell.getName().equals("Approach of the Second Sun")) {
                approachesCast.put(event.getPlayerId(), getApproachesCast(event.getPlayerId()) + 1);
            }
        }
    }

    public int getApproachesCast(UUID player) {
        return approachesCast.getOrDefault(player, 0);
    }

}
