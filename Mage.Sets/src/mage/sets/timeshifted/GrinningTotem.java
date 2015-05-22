/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.timeshifted;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class GrinningTotem extends CardImpl {

    public GrinningTotem(UUID ownerId) {
        super(ownerId, 110, "Grinning Totem", Rarity.SPECIAL, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "TSB";

        // {2}, {tap}, Sacrifice Grinning Totem: Search target opponent's library for a card and exile it. Then that player shuffles his or her library.
        // Until the beginning of your next upkeep, you may play that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrinningTotemSearchAndExileEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        // At the beginning of your next upkeep, if you haven't played it, put it into its owner's graveyard.
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new GrinningTotemDelayedTriggeredAbility()));
        
        this.addAbility(ability);
    }

    public GrinningTotem(final GrinningTotem card) {
        super(card);
    }

    @Override
    public GrinningTotem copy() {
        return new GrinningTotem(this);
    }
}

class GrinningTotemSearchAndExileEffect extends OneShotEffect {

    public GrinningTotemSearchAndExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target opponent's library for a card and exile it. Then that player shuffles his or her library. Until the beginning of your next upkeep, you may play that card";
    }
    
    public GrinningTotemSearchAndExileEffect(final GrinningTotemSearchAndExileEffect effect) {
        super(effect);
    }

    @Override
    public GrinningTotemSearchAndExileEffect copy() {
        return new GrinningTotemSearchAndExileEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        MageObject sourcObject = game.getObject(source.getSourceId());
        if (you != null && targetOpponent != null) {
            if (targetOpponent.getLibrary().size() > 0) {
                TargetCardInLibrary targetCard = new TargetCardInLibrary();
                if (you.searchLibrary(targetCard, game, targetOpponent.getId())) {
                    Card card = targetOpponent.getLibrary().remove(targetCard.getFirstTarget(), game);
                    if (card != null) {
                        you.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source), sourcObject != null ? sourcObject.getName() : "", source.getSourceId(), game, Zone.LIBRARY, true);
                        ContinuousEffect effect = new GrinningTotemMayPlayEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId()));
                        game.addEffect(effect, source);
                    }                    
                }
            }
            targetOpponent.shuffleLibrary(game);
            return true;
        }
        return false;
    }
    
}

class GrinningTotemMayPlayEffect extends AsThoughEffectImpl {

    public GrinningTotemMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the beginning of your next upkeep, you may play that card.";
    }
    
    public GrinningTotemMayPlayEffect(final GrinningTotemMayPlayEffect effect) {
        super(effect);
    }

    @Override
    public GrinningTotemMayPlayEffect copy() {
        return new GrinningTotemMayPlayEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(sourceId)) {
            return game.getState().getZone(sourceId).equals(Zone.EXILED);
        }
        return false;        
    }
    
}

class GrinningTotemDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public GrinningTotemDelayedTriggeredAbility() {
        super(new GrinningTotemPutIntoGraveyardEffect());
    }

    public GrinningTotemDelayedTriggeredAbility(final GrinningTotemDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, this.getSourceId()));        
        return exileZone != null && exileZone.getCards(game).size() > 0;
    }

    @Override
    public GrinningTotemDelayedTriggeredAbility copy() {
        return new GrinningTotemDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && game.getActivePlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "At the beginning of your next upkeep, if you haven't played it, " + modes.getText();
    }
}


class GrinningTotemYouHaveNotPlayedCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        return zone.getCards(game).size() > 0;
    }
    
}

class GrinningTotemPutIntoGraveyardEffect extends OneShotEffect {

    public GrinningTotemPutIntoGraveyardEffect() {
        super(Outcome.Detriment);
        this.staticText = "put it into its owner's graveyard";
    }
    
    public GrinningTotemPutIntoGraveyardEffect(final GrinningTotemPutIntoGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public GrinningTotemPutIntoGraveyardEffect copy() {
        return new GrinningTotemPutIntoGraveyardEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (controller != null && zone != null) {
            return controller.moveCards(zone, Zone.EXILED, Zone.GRAVEYARD, source, game);
        }
        return false;
    }
    
}
