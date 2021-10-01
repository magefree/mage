package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DecayedAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author weirddan455
 */
public final class GhoulsNightOut extends CardImpl {

    public GhoulsNightOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // For each player, choose a creature card in that player's graveyard. Put those cards onto the battlefield under your control. They're black Zombies in addition to their other colors and types and they gain decayed.
        this.getSpellAbility().addEffect(new GhoulsNightOutEffect());
    }

    private GhoulsNightOut(final GhoulsNightOut card) {
        super(card);
    }

    @Override
    public GhoulsNightOut copy() {
        return new GhoulsNightOut(this);
    }
}

class GhoulsNightOutEffect extends OneShotEffect {

    public GhoulsNightOutEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "For each player, choose a creature card in that player's graveyard. Put those cards onto the battlefield under your control. They're black Zombies in addition to their other colors and types and they gain decayed";
    }

    private GhoulsNightOutEffect(final GhoulsNightOutEffect effect) {
        super(effect);
    }

    @Override
    public GhoulsNightOutEffect copy() {
        return new GhoulsNightOutEffect(this);
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
                    boolean creatureInGraveyard = false;
                    for (UUID cardId : player.getGraveyard()) {
                        Card card = game.getCard(cardId);
                        if (card != null && card.isCreature(game)) {
                            creatureInGraveyard = true;
                            break;
                        }
                    }
                    if (creatureInGraveyard) {
                        FilterCreatureCard filter = new FilterCreatureCard("creature card in " + player.getName() + "'s graveyard");
                        TargetCard target = new TargetCard(Zone.GRAVEYARD, filter);
                        target.setNotTarget(true);
                        controller.chooseTarget(controllerId.equals(playerId) ? Outcome.Benefit : Outcome.Detriment, player.getGraveyard(), target, source, game);
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            cardsToBattlefield.add(card);
                        }
                    }
                }
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
                cardsToBattlefield.removeIf(card -> game.getState().getZone(card.getId()) != Zone.BATTLEFIELD);
                if (!cardsToBattlefield.isEmpty()) {
                    game.addEffect(new GhoulsNightOutTypeChangingEffect(
                    ).setTargetPointer(new FixedTargets(cardsToBattlefield, game)), source);
                    game.addEffect(new GainAbilityTargetEffect(
                            new DecayedAbility(), Duration.Custom
                    ).setTargetPointer(new FixedTargets(cardsToBattlefield, game)), source);
                    return true;
                }
            }
        }
        return false;
    }
}

class GhoulsNightOutTypeChangingEffect extends ContinuousEffectImpl {

    public GhoulsNightOutTypeChangingEffect() {
        super(Duration.Custom, Outcome.Neutral);
    }

    private GhoulsNightOutTypeChangingEffect(final GhoulsNightOutTypeChangingEffect effect) {
        super(effect);
    }

    @Override
    public GhoulsNightOutTypeChangingEffect copy() {
        return new GhoulsNightOutTypeChangingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer subLayer, Ability source, Game game) {
        boolean isActive = false;
        for (UUID permId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permId);
            if (permanent != null) {
                switch (layer) {
                    case ColorChangingEffects_5:
                        permanent.getColor(game).setBlack(true);
                        isActive = true;
                        break;
                    case TypeChangingEffects_4:
                        permanent.addSubType(game, SubType.ZOMBIE);
                        isActive = true;
                        break;
                }
            }
        }
        return isActive;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }
}
