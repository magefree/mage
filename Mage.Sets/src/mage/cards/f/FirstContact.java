package mage.cards.f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.Watcher;

/**
 *
 * @author muz
 */
public final class FirstContact extends CardImpl {

    public FirstContact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Mill four cards. You may put a permanent card from among them into your hand. If this spell is the first spell you've cast this game, you gain 2 life.
        this.getSpellAbility().addEffect(new FirstContactEffect());
        this.getSpellAbility().addWatcher(new FirstContactWatcher());
    }

    private FirstContact(final FirstContact card) {
        super(card);
    }

    @Override
    public FirstContact copy() {
        return new FirstContact(this);
    }
}

class FirstContactEffect extends OneShotEffect {

    FirstContactEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards. You may put a permanent card from among them into your hand. " +
            "If this spell is the first spell you've cast this game, you gain 2 life";
    }

    private FirstContactEffect(final FirstContactEffect effect) {
        super(effect);
    }

    @Override
    public FirstContactEffect copy() {
        return new FirstContactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(4, source, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCard(0, 1, Zone.ALL, StaticFilters.FILTER_CARD_PERMANENT);
        target.withNotTarget(true);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        FirstContactWatcher watcher = game.getState().getWatcher(FirstContactWatcher.class);
        if (watcher != null && watcher.getSpellsCastThisGame(source.getControllerId())) {
            player.gainLife(2, game, source);
        }
        return true;
    }
}

class FirstContactWatcher extends Watcher {

    private final Map<UUID, Integer> castSpells = new HashMap<>();

    FirstContactWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
                Spell spell = (Spell) game.getObject(event.getTargetId());
                if (spell == null) {
                    return;
                }
                UUID playerId = spell.getControllerId();
                castSpells.put(playerId, castSpells.getOrDefault(playerId, 0) + 1);
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    castSpells.clear();
                }
        }
    }

    boolean getSpellsCastThisGame(UUID playerId) {
        return castSpells.getOrDefault(playerId, 0) == 1;
    }
}
