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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class SinsOfThePast extends CardImpl {

    public SinsOfThePast(UUID ownerId) {
        super(ownerId, 106, "Sins of the Past", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");
        this.expansionSetCode = "RAV";

        // Until end of turn, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead. Exile Sins of the Past.
        this.getSpellAbility().addEffect(new SinsOfThePastEffect());
        this.getSpellAbility().addEffect(new ExileSourceEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterInstantOrSorceryCard()));
    }

    public SinsOfThePast(final SinsOfThePast card) {
        super(card);
    }

    @Override
    public SinsOfThePast copy() {
        return new SinsOfThePast(this);
    }
}

class SinsOfThePastEffect extends OneShotEffect {
    
    SinsOfThePastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Until end of turn, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead";
    }
    
    SinsOfThePastEffect(final SinsOfThePastEffect effect) {
        super(effect);
    }
    
    @Override
    public SinsOfThePastEffect copy() {
        return new SinsOfThePastEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            ContinuousEffect effect = new SinsOfThePastCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            game.addEffect(effect, source);
            effect = new SinsOfThePastReplacementEffect(card.getId());
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class SinsOfThePastCastFromGraveyardEffect extends AsThoughEffectImpl {

    SinsOfThePastCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfTurn, Outcome.PlayForFree);
    }

    SinsOfThePastCastFromGraveyardEffect(final SinsOfThePastCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SinsOfThePastCastFromGraveyardEffect copy() {
        return new SinsOfThePastCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        /*if (this.getTargetPointer().getFirst(game, source) == null) {
            discard();
            return false;
        }*/
        
        if (sourceId.equals(this.getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId())) {
            Player player = game.getPlayer(affectedControllerId);
            player.setCastSourceIdWithoutMana(sourceId);
            return true;
        }
        return false;
    }
}

class SinsOfThePastReplacementEffect extends ReplacementEffectImpl {
    
    private final UUID cardId;

    SinsOfThePastReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
    }

    SinsOfThePastReplacementEffect(final SinsOfThePastReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public SinsOfThePastReplacementEffect copy() {
        return new SinsOfThePastReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.cardId);
        if (controller != null && card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.STACK, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        game.informPlayers(event.getType().name());
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTargetId().equals(this.cardId);
    }
}
