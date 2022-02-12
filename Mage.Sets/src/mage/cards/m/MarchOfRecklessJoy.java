/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.m;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.CostsLessForExiledCardsEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 */
public final class MarchOfRecklessJoy extends CardImpl {

    private static final FilterCard filter = new FilterCard("red cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public MarchOfRecklessJoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // As an additional cost to cast this spell, you may exile any number of red cards from your hand. This spell costs {2} less to cast for each card exiled this way.
        CostsLessForExiledCardsEffect.addCostAndEffect(this, filter);

        // Exile the top X cards of your library. You may play up to two of those cards until the end of your next turn.
        this.getSpellAbility().addEffect(new MarchOfRecklessJoyEffect());
        this.getSpellAbility().addWatcher(new MarchOfRecklessJoyWatcher(super.getId()));

    }

    private MarchOfRecklessJoy(final MarchOfRecklessJoy card) {
        super(card);
    }

    @Override
    public MarchOfRecklessJoy copy() {
        return new MarchOfRecklessJoy(this);
    }
}

class MarchOfRecklessJoyEffect extends OneShotEffect {

    Cards storeCardsThatWereInExile = new CardsImpl();
    Set<Card> cardsToExile = new HashSet<>();

    public MarchOfRecklessJoyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top X cards of your library. You may play up to two of those cards until the end of your next turn.";
    }

    public MarchOfRecklessJoyEffect(final MarchOfRecklessJoyEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfRecklessJoyEffect copy() {
        return new MarchOfRecklessJoyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        storeCardsThatWereInExile.clear();
        cardsToExile.clear();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString(), game);
            cardsToExile = controller.getLibrary().getTopCards(game, ManacostVariableValue.REGULAR.calculate(game, source, this));
            controller.moveCardsToExile(cardsToExile, source, game, true, exileId, game.getObject(source.getSourceId()).getIdName());
            MarchOfRecklessJoyPlayEffect effect = new MarchOfRecklessJoyPlayEffect();
            ConditionalAsThoughEffect conditionalEffect = new ConditionalAsThoughEffect(effect, new MarchOfRecklessJoyCondition());
            conditionalEffect.setDuration(Duration.UntilEndOfYourNextTurn);
            game.getState().setValue("Result" + source.getSourceId().toString(), Boolean.TRUE);
            for (Card exiledCard : cardsToExile) {
                if (game.getState().getZone(exiledCard.getId()) == Zone.EXILED) {
                    storeCardsThatWereInExile.add(exiledCard);
                    conditionalEffect.setTargetPointer(new FixedTarget(exiledCard.getId(), game));
                    game.addEffect(conditionalEffect, source);
                }
            }
            game.getState().setValue("Cards Exile" + source.getSourceId().toString(), storeCardsThatWereInExile);
            game.getState().setValue("Cards Cast From Exile So Far" + source.getSourceId().toString(), 0);
            return true;
        }
        return false;
    }
}

class MarchOfRecklessJoyCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatcher(MarchOfRecklessJoyWatcher.class);
        return (watcher != null
                && watcher.conditionMet());
    }
}

class MarchOfRecklessJoyWatcher extends Watcher {

    UUID sourceCardId;

    public MarchOfRecklessJoyWatcher(UUID sourceCardId) {
        super(WatcherScope.GAME);
        this.sourceCardId = sourceCardId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        int numberCastSoFar = 0;
        if (game.getState().getValue("Result" + sourceCardId.toString()) != null) {
            condition = (Boolean) game.getState().getValue("Result" + sourceCardId.toString());
        }
        if (game.getState().getValue("Cards Cast From Exile So Far" + sourceCardId) != null) {
            numberCastSoFar = (int) game.getState().getValue("Cards Cast From Exile So Far" + sourceCardId);
        }
        UUID exileId = CardUtil.getExileZoneId(sourceCardId.toString(), game);
        if (game.getState().getExile().getExileZone(exileId) != null
                && game.getState().getExile().getExileZone(exileId).size() > 0) {
            Cards cards = (Cards) game.getState().getValue("Cards Exile" + sourceCardId.toString());
            // Cast spell
            if (event.getType() == GameEvent.EventType.SPELL_CAST) {
                Spell spell = (Spell) game.getObject(event.getSourceId());
                if (spell != null
                        && cards != null
                        && !cards.isEmpty()
                        && cards.contains(spell.getSourceId())) {
                    Ability approvingAbility = event.getAdditionalReference().getApprovingAbility();
                    if (approvingAbility != null
                            && approvingAbility.getSourceId().equals(sourceCardId)) {
                        numberCastSoFar += 1;
                        game.getState().setValue("Cards Cast From Exile So Far" + sourceCardId, numberCastSoFar);
                        if (numberCastSoFar > 1) {
                            condition = false;
                            game.getState().setValue("Result" + sourceCardId.toString(), Boolean.FALSE);
                        }
                    }
                }
            }
            // Play land
            if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
                Card land = game.getCard(event.getTargetId());
                if (land != null
                        && cards != null
                        && !cards.isEmpty()
                        && cards.contains(land.getId())) {
                    Ability approvingAbility = event.getAdditionalReference().getApprovingAbility();
                    if (approvingAbility != null
                            && approvingAbility.getSourceId().equals(sourceCardId)) {
                        numberCastSoFar += 1;
                        game.getState().setValue("Cards Cast From Exile So Far" + sourceCardId, numberCastSoFar);
                        if (numberCastSoFar > 1) {
                            condition = false;
                            game.getState().setValue("Result" + sourceCardId.toString(), Boolean.FALSE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}

class MarchOfRecklessJoyPlayEffect extends AsThoughEffectImpl {

    MarchOfRecklessJoyPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
        staticText = "";
    }

    private MarchOfRecklessJoyPlayEffect(final MarchOfRecklessJoyPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MarchOfRecklessJoyPlayEffect copy() {
        return new MarchOfRecklessJoyPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {

        UUID exileId = CardUtil.getExileZoneId(source.getSourceId().toString(), game);

        Set<Card> cards = game.getState().getExile().getExileZone(exileId).getCards(game);
        List<UUID> targetsTest = getTargetPointer().getTargets(game, source);
        if (cards != null
                && targetsTest != null) {
            for (UUID uuid : targetsTest) {
                if (!cards.contains(game.getCard(uuid))) {
                    getTargetPointer().getTargets(game, source).remove(uuid);
                }
            }
        }

        if (game.getState().getValue("Result" + source.getSourceId().toString()) == Boolean.FALSE) {
            this.discard();
            return false;
        }

        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }

        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId);
        if (!targets.contains(objectIdToCast)) {
            return false;
        }

        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be in exile
        if (game.getState().getZone(objectId) != Zone.EXILED) {
            return false;
        }

        // allow it
        return true;
    }
}
