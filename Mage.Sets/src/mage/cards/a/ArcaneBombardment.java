package mage.cards.a;

import mage.ApprovingObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

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

        // Whenever you cast your first instant or sorcery spell each turn, exile an instant or sorcery card at 
        // random from your graveyard. Then copy each card exiled with Arcane Bombardment. 
        // You may cast any number of the copies without paying their mana costs.
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
        super(Outcome.PlayForFree);
        this.staticText = "exile an instant or sorcery card at random from your graveyard. "
                + "Then copy each card exiled with {this}, and you may cast any number of the copies without paying their mana costs.";
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

        // Exile a card at random from graveyard
        Cards cardsInGraveyard = new CardsImpl(player.getGraveyard().getCards(new FilterInstantOrSorceryCard(), game));
        if (cardsInGraveyard.isEmpty()) {
            return false;
        }

        Card cardToExile = cardsInGraveyard.getRandom(game);
        if (cardToExile == null) {
            return false;
        }

        player.moveCards(cardToExile, Zone.EXILED, source, game);
        MageObjectReference mor = new MageObjectReference(cardToExile, game);

        // Get the list of exiled cards from the game state or create a new one
        List<MageObjectReference> exiledCards = (List<MageObjectReference>) game.getState().getValue(source.getSourceId() + "_exiledCards");
        if (exiledCards == null) {
            exiledCards = new ArrayList<>();
            game.getState().setValue(source.getSourceId() + "_exiledCards", exiledCards);
        }
        exiledCards.add(mor);

        // Copy the exiled cards
        Cards copies = new CardsImpl();
        for (MageObjectReference reference : exiledCards) {
            Card card = reference.getCard(game);
            if (card != null) {
                Card copiedCard = game.copyCard(card, source, source.getControllerId());
                copies.add(copiedCard);
            }
        }

        // Allow player to choose the order and cast the copies
        if (!copies.isEmpty()) {
            TargetCard target = new TargetCard(0, copies.size(), Zone.EXILED, new FilterCard("copies to cast"));
            player.choose(Outcome.PlayForFree, copies, target, source, game);
            List<UUID> targets = target.getTargets();

            for (UUID targetId : targets) {
                Card copiedCard = game.getCard(targetId);
                if (copiedCard != null && copiedCard.getSpellAbility() != null) {
                    if (player.chooseUse(Outcome.PlayForFree, "Cast the copy of " + copiedCard.getLogName() + "?", source, game)) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                        player.cast(player.chooseAbilityForCast(copiedCard, game, true), game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
                    }
                }
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
                .getWatcher(ArcaneBombardmentWatcher.class).playerMap
                .getOrDefault(input.getControllerId(), 0) < 2;
    }
}
