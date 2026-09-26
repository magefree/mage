package mage.cards.e;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.SurveilledEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author brahle
 */
public final class EyeOfDuskmantle extends CardImpl {

    public EyeOfDuskmantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.EYE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // You may play lands and cast spells from among cards in your graveyard you've surveilled this turn.
        // If you cast a spell this way, you pay life equal to its mana value rather than paying its mana cost.
        Ability ability = new SimpleStaticAbility(new EyeOfDuskmantlePlayEffect());
        ability.setIdentifier(MageIdentifier.EyeOfDuskmantleAlternateCast);
        ability.addWatcher(new EyeOfDuskmantleWatcher());
        this.addAbility(ability);
    }

    private EyeOfDuskmantle(final EyeOfDuskmantle card) {
        super(card);
    }

    @Override
    public EyeOfDuskmantle copy() {
        return new EyeOfDuskmantle(this);
    }
}

class EyeOfDuskmantlePlayEffect extends AsThoughEffectImpl {

    EyeOfDuskmantlePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may play lands and cast spells from among cards in your graveyard you've surveilled this turn. "
                + "If you cast a spell this way, you pay life equal to its mana value rather than paying its mana cost";
    }

    private EyeOfDuskmantlePlayEffect(final EyeOfDuskmantlePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EyeOfDuskmantlePlayEffect copy() {
        return new EyeOfDuskmantlePlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || !card.isOwnedBy(affectedControllerId)) {
            return false;
        }
        if (!Zone.GRAVEYARD.equals(game.getState().getZone(card.getMainCard().getId()))) {
            return false;
        }
        EyeOfDuskmantleWatcher watcher = game.getState().getWatcher(EyeOfDuskmantleWatcher.class);
        if (watcher == null || !watcher.isSurveilledThisTurn(affectedControllerId, card, game)) {
            return false;
        }
        if (!card.isLand(game)) {
            PayLifeCost lifeCost = new PayLifeCost(card.getSpellAbility().getManaCosts().manaValue());
            Costs newCosts = new CostsImpl();
            newCosts.add(lifeCost);
            newCosts.addAll(card.getSpellAbility().getCosts());
            Player player = game.getPlayer(affectedControllerId);
            if (player != null) {
                player.setCastSourceIdWithAlternateMana(card.getId(), null, newCosts, MageIdentifier.EyeOfDuskmantleAlternateCast);
            }
        }
        return true;
    }
}

class EyeOfDuskmantleWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> surveilledCards = new HashMap<>();

    EyeOfDuskmantleWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SURVEILED && event instanceof SurveilledEvent) {
            SurveilledEvent sEvent = (SurveilledEvent) event;
            for (Card card : sEvent.getSurveilledToGraveyard().getCards(game)) {
                surveilledCards.computeIfAbsent(sEvent.getPlayerId(), k -> new HashSet<>())
                        .add(new MageObjectReference(card.getMainCard(), game));
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        surveilledCards.clear();
    }

    public boolean isSurveilledThisTurn(UUID playerId, Card card, Game game) {
        Set<MageObjectReference> set = surveilledCards.get(playerId);
        if (set == null) {
            return false;
        }
        return set.contains(new MageObjectReference(card.getMainCard(), game));
    }
}
