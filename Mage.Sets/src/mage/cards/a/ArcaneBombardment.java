package mage.cards.a;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcaneBombardment extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("your first instant or sorcery spell each turn");

    static {
        filter.add(ArcaneBombardmentWatcher::checkSpell);
    }

    public ArcaneBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");

        // Whenever you cast your first instant or sorcery spell each turn, exile an instant or sorcery card at random from your graveyard. Then copy each card exiled with Arcane Bombardment. You may cast any number of the copies without paying their mana costs.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ArcaneBombardmentEffect(), filter, false
        ), new ArcaneBombardmentWatcher());
    }

    private ArcaneBombardment(final ArcaneBombardment card) {
        super(card);
    }

    @Override
    public ArcaneBombardment copy() {
        return new ArcaneBombardment(this);
    }
}

class ArcaneBombardmentEffect extends OneShotEffect {

    ArcaneBombardmentEffect() {
        super(Outcome.Benefit);
        staticText = "exile an instant or sorcery card at random from your graveyard. Then copy each " +
                "card exiled with {this}. You may cast any number of the copies without paying their mana costs";
    }

    private ArcaneBombardmentEffect(final ArcaneBombardmentEffect effect) {
        super(effect);
    }

    @Override
    public ArcaneBombardmentEffect copy() {
        return new ArcaneBombardmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        Card toExile = RandomUtil.randomFromCollection(
                player.getGraveyard().getCards(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game)
        );
        if (toExile != null) {
            player.moveCardsToExile(
                    toExile, source, game, true,
                    exileId, CardUtil.getSourceName(game, source)
            );
        }
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }

        Cards copies = new CardsImpl();
        for (Card card : exileZone.getCards(game)) {
            Card copiedCard = game.copyCard(card, source, source.getControllerId());
            copies.add(copiedCard);
        }
        for (Card copiedCard : copies.getCards(game)) {
            if (!player.chooseUse(outcome, "Cast the copied card?", source, game)) {
                continue;
            }
            if (copiedCard.getSpellAbility() != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                player.cast(
                        player.chooseAbilityForCast(copiedCard, game, true),
                        game, true, new ApprovingObject(source, game)
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
            } else {
                Logger.getLogger(ArcaneBombardmentEffect.class).error("Arcane Bombardment: "
                        + "spell ability == null " + copiedCard.getName());
            }
        }
        return true;
    }
}

class ArcaneBombardmentWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    ArcaneBombardmentWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null && spell.isInstantOrSorcery(game)) {
            playerMap.compute(spell.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static boolean checkSpell(StackObject input, Game game) {
        return game
                .getState()
                .getWatcher(ArcaneBombardmentWatcher.class)
                .playerMap
                .getOrDefault(input.getControllerId(), 0) < 2;
    }
}
