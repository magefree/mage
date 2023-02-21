package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BreachTheMultiverse extends CardImpl {
    public BreachTheMultiverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        //Each player mills ten cards. For each player, choose a creature or planeswalker card in that player’s
        //graveyard. Put those cards onto the battlefield under your control. Then each creature you control becomes a
        //Phyrexian in addition to its other types.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(10, TargetController.EACH_PLAYER));
        this.getSpellAbility().addEffect(new BreachTheMultiverseEffect());
        this.getSpellAbility().addEffect(new BreachTheMultiverseSubtypeEffect());
    }

    private BreachTheMultiverse(final BreachTheMultiverse card) {
        super(card);
    }

    @Override
    public BreachTheMultiverse copy() {
        return new BreachTheMultiverse(this);
    }
}

class BreachTheMultiverseEffect extends OneShotEffect {

    public BreachTheMultiverseEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "for each player, choose a creature or planeswalker card in that player's graveyard. Put those " +
                "cards onto the battlefield under your control.";
    }

    private BreachTheMultiverseEffect(final BreachTheMultiverseEffect effect) {
        super(effect);
    }

    @Override
    public BreachTheMultiverseEffect copy() {
        return new BreachTheMultiverseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Set<Card> cardsToBattlefield = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    boolean creatureOrPlaneswalkerInGraveyard = false;
                    for (UUID cardId : player.getGraveyard()) {
                        Card card = game.getCard(cardId);
                        if (card != null && (card.isCreature(game) || card.isPlaneswalker(game))) {
                            creatureOrPlaneswalkerInGraveyard = true;
                            break;
                        }
                    }
                    if (creatureOrPlaneswalkerInGraveyard) {
                        FilterCreatureOrPlaneswalkerCard filter = new FilterCreatureOrPlaneswalkerCard(
                                "creature or planeswalker card in " + player.getName() + "'s graveyard");
                        TargetCard target = new TargetCard(Zone.GRAVEYARD, filter);
                        target.setNotTarget(true);
                        controller.chooseTarget(controllerId.equals(playerId) ? Outcome.Benefit : Outcome.Detriment,
                                player.getGraveyard(), target, source, game);
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            cardsToBattlefield.add(card);
                        }
                    }
                }
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
                return true;
            }
        }
        return false;
    }
}

class BreachTheMultiverseSubtypeEffect extends ContinuousEffectImpl {

    BreachTheMultiverseSubtypeEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Then each creature you control becomes a Phyrexian in addition to its other types";
    }

    private BreachTheMultiverseSubtypeEffect(final BreachTheMultiverseSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
        );
        for (Permanent permanent : permanents) {
            if (permanent == null) {
                continue;
            }
            permanent.addSubType(game, SubType.PHYREXIAN);
        }
        return true;
    }

    @Override
    public BreachTheMultiverseSubtypeEffect copy() {
        return new BreachTheMultiverseSubtypeEffect(this);
    }
}
