/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.n;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.WatcherScope;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class NashiMoonSagesScion extends CardImpl {

    public NashiMoonSagesScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {3}{B}
        this.addAbility(new NinjutsuAbility("{3}{B}"));

        // Whenever Nashi, Moon Sage's Scion deals combat damage to a player, exile the top card of each player's library. Until end of turn, you may play one of those cards. If you cast a spell this way, pay life equal to its mana value rather than paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NashiMoonSagesScionEffect(), false), new NashiMoonSagesScionWatcher(super.getId()));

    }

    private NashiMoonSagesScion(final NashiMoonSagesScion card) {
        super(card);
    }

    @Override
    public NashiMoonSagesScion copy() {
        return new NashiMoonSagesScion(this);
    }
}

class NashiMoonSagesScionEffect extends OneShotEffect {

    Cards storeCardsThatWereInExile = new CardsImpl();
    Set<Card> cardsToExile = new HashSet<>();

    public NashiMoonSagesScionEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of each player's library. Until end of turn, you may play one of those cards. If you cast a spell this way, pay life equal to its mana value rather than paying its mana cost";
    }

    public NashiMoonSagesScionEffect(final NashiMoonSagesScionEffect effect) {
        super(effect);
    }

    @Override
    public NashiMoonSagesScionEffect copy() {
        return new NashiMoonSagesScionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        storeCardsThatWereInExile.clear();
        cardsToExile.clear();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getTurnNum());
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Card topCard = player.getLibrary().getFromTop(game);
                    cardsToExile.add(topCard);
                }
            }
            controller.moveCardsToExile(cardsToExile, source, game, true, exileId, game.getObject(source.getSourceId()).getIdName());
            NashiMoonSagesScionPlayEffect effect = new NashiMoonSagesScionPlayEffect();
            ConditionalAsThoughEffect conditionalEffect = new ConditionalAsThoughEffect(effect, new NashiMoonSagesScionCondition());
            conditionalEffect.setDuration(Duration.EndOfTurn);
            game.getState().setValue("Result" + source.getSourceId().toString() + game.getTurn().toString(), Boolean.TRUE);
            for (Card exiledCard : cardsToExile) {
                if (game.getState().getZone(exiledCard.getId()) == Zone.EXILED) {
                    storeCardsThatWereInExile.add(exiledCard);
                    conditionalEffect.setTargetPointer(new FixedTarget(exiledCard.getId(), game));
                    game.addEffect(conditionalEffect, source);
                }
            }
            game.getState().setValue("Cards Exile" + source.getSourceId().toString() + game.getTurn().toString(), storeCardsThatWereInExile);
            return true;
        }
        return false;
    }
}

class NashiMoonSagesScionCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatcher(NashiMoonSagesScionWatcher.class);
        return (watcher != null
                && watcher.conditionMet());
    }
}

class NashiMoonSagesScionWatcher extends Watcher {

    UUID sourceCardId;

    public NashiMoonSagesScionWatcher(UUID sourceCardId) {
        super(WatcherScope.GAME);
        this.sourceCardId = sourceCardId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (game.getState().getValue("Result" + sourceCardId.toString() + game.getTurn().toString()) != null) {
            condition = (Boolean) game.getState().getValue("Result" + sourceCardId.toString() + game.getTurn().toString());
        }
        UUID exileId = CardUtil.getExileZoneId(game, sourceCardId, game.getTurnNum());
        if (game.getState().getExile().getExileZone(exileId) != null
                && game.getState().getExile().getExileZone(exileId).size() > 0) {
            Cards cards = (Cards) game.getState().getValue("Cards Exile" + sourceCardId.toString() + game.getTurn().toString());
            // Cast spell
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                Spell spell = (Spell) game.getObject(event.getSourceId());
                if (spell != null
                        && cards != null
                        && !cards.isEmpty()
                        && cards.contains(spell.getSourceId())) {
                    Ability approvingAbility = event.getAdditionalReference().getApprovingAbility();
                    if (approvingAbility != null
                            && approvingAbility.getSourceId().equals(sourceCardId)) {
                        condition = false;
                        game.getState().setValue("Result" + sourceCardId.toString() + game.getTurn().toString(), Boolean.FALSE);
                    }
                }
            }
            // Play land
            if (event.getType() == GameEvent.EventType.PLAY_LAND) {
                Card land = game.getCard(event.getSourceId());
                if (land != null
                        && cards != null
                        && !cards.isEmpty()
                        && cards.contains(land.getId())) {
                    Ability approvingAbility = event.getAdditionalReference().getApprovingAbility();
                    if (approvingAbility != null
                            && approvingAbility.getSourceId().equals(sourceCardId)) {
                        condition = false;
                        game.getState().setValue("Result" + sourceCardId.toString() + game.getTurn().toString(), Boolean.FALSE);
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

class NashiMoonSagesScionPlayEffect extends AsThoughEffectImpl {

    NashiMoonSagesScionPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.EndOfTurn, Outcome.Benefit);
        staticText = "";
    }

    private NashiMoonSagesScionPlayEffect(final NashiMoonSagesScionPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NashiMoonSagesScionPlayEffect copy() {
        return new NashiMoonSagesScionPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {

        Player controller = game.getPlayer(affectedControllerId);
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getTurnNum());

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

        if (game.getState().getValue("Result" + source.getSourceId().toString() + game.getTurn().toString()) == Boolean.FALSE) {
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

        // allows to play/cast with alternative life cost
        if (!cardToCheck.isLand(game)) {
            PayLifeCost lifeCost = new PayLifeCost(cardToCheck.getSpellAbility().getManaCosts().manaValue());
            Costs newCosts = new CostsImpl();
            newCosts.add(lifeCost);
            newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
            controller.setCastSourceIdWithAlternateMana(cardToCheck.getId(), null, newCosts);
        }
        return true;
    }
}
