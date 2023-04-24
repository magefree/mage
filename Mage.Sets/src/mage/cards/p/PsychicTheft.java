package mage.cards.p;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author L_J (significantly based on code by jeffwadsworth and Styxo)
 */
public final class PsychicTheft extends CardImpl {

    public PsychicTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target player reveals their hand. You choose an instant or sorcery card from it and exile that card. You may cast that card for as long as it remains exiled. At the beginning of the next end step, if you haven't cast the card, return it to its owner's hand.
        this.getSpellAbility().addEffect(new PsychicTheftEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new PsychicTheftWatcher());
    }

    private PsychicTheft(final PsychicTheft card) {
        super(card);
    }

    @Override
    public PsychicTheft copy() {
        return new PsychicTheft(this);
    }
}

class PsychicTheftEffect extends OneShotEffect {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    PsychicTheftEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals their hand. You choose an instant or sorcery card from it " +
                "and exile that card. You may cast that card for as long as it remains exiled. " +
                "At the beginning of the next end step, if you haven't cast the card, return it to its owner's hand.";
    }

    private PsychicTheftEffect(final PsychicTheftEffect effect) {
        super(effect);
    }

    @Override
    public PsychicTheftEffect copy() {
        return new PsychicTheftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        opponent.revealCards(CardUtil.getSourceName(game, source), opponent.getHand(), game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int cardsHand = opponent.getHand().count(filter, game);
        Card chosenCard = null;
        if (cardsHand > 0) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            if (controller.choose(Outcome.Exile, opponent.getHand(), target, source, game)) {
                chosenCard = opponent.getHand().get(target.getFirstTarget(), game);
            }
        }
        if (chosenCard == null) {
            return false;
        }
        controller.moveCardToExileWithInfo(chosenCard, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source), source, game, Zone.HAND, true);

        CardUtil.makeCardPlayable(game, source, chosenCard, Duration.Custom, false);

        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ConditionalOneShotEffect(
                        new ReturnFromExileEffect(Zone.HAND),
                        new PsychicTheftCondition(chosenCard, game),
                        "if you haven't cast it, return it to its owner's hand."
                )
        ), source);
        return true;
    }
}

class PsychicTheftCondition implements Condition {
    private final MageObjectReference mor;

    PsychicTheftCondition(Card card, Game game) {
        this.mor = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PsychicTheftWatcher watcher = game.getState().getWatcher(PsychicTheftWatcher.class);
        return watcher != null && !watcher.checkPlayer(source.getSourceId(), mor);
    }
}

class PsychicTheftWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    PsychicTheftWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || spell.getCard() == null || spell.getCard().getMainCard() == null) {
            return;
        }
        map.computeIfAbsent(event.getPlayerId(), x -> new HashSet<>()).add(new MageObjectReference(spell.getCard().getMainCard(), game));
    }

    boolean checkPlayer(UUID playerId, MageObjectReference mor) {
        return map.computeIfAbsent(playerId, x -> new HashSet<>()).contains(mor);
    }
}
