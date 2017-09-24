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
package mage.cards.p;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;


/**
 *
 * @author L_J (significantly based on code by jeffwadsworth and Styxo)
 */
public class PsychicTheft extends CardImpl {

    public PsychicTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Target player reveals his or her hand. You choose an instant or sorcery card from it and exile that card. You may cast that card for as long as it remains exiled. At the beginning of the next end step, if you haven't cast the card, return it to its owner's hand.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PsychicTheftEffect());

    }

    public PsychicTheft(final PsychicTheft card) {
        super(card);
    }

    @Override
    public PsychicTheft copy() {
        return new PsychicTheft(this);
    }
}

class PsychicTheftEffect extends OneShotEffect {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    public PsychicTheftEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals his or her hand. You choose an instant or sorcery card from it and exile that card. You may cast that card for as long as it remains exiled. At the beginning of the next end step, if you haven't cast the card, return it to its owner's hand.";
    }

    public PsychicTheftEffect(final PsychicTheftEffect effect) {
        super(effect);
    }

    @Override
    public PsychicTheftEffect copy() {
        return new PsychicTheftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (opponent != null && sourceObject != null) {
            opponent.revealCards(sourceObject.getName(), opponent.getHand(), game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int cardsHand = opponent.getHand().count(filter, game);
                Card chosenCard = null;
                if (cardsHand > 0) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    if (controller.choose(Outcome.Benefit, opponent.getHand(), target, game)) {
                        chosenCard = opponent.getHand().get(target.getFirstTarget(), game);
                    }
                }
                if (chosenCard != null) {
                    
                    opponent.moveCardToExileWithInfo(chosenCard, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.HAND, true);
                
                    AsThoughEffect effect = new PsychicTheftCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(chosenCard.getId()));
                    game.addEffect(effect, source);
                
                    OneShotEffect effect2 = new ReturnFromExileEffect(source.getSourceId(), Zone.HAND);
                    Condition condition = new SpellWasNotCastCondition(source.getSourceId(), chosenCard.getId());
                
                    ConditionalOneShotEffect effect3 = new ConditionalOneShotEffect(effect2, condition, "if you haven't cast it, return it to its owner's hand.");
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect3);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                    return true;
                }
            }
        }
        return false;
    }
}

class PsychicTheftCastFromExileEffect extends AsThoughEffectImpl {

    PsychicTheftCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled";
    }

    PsychicTheftCastFromExileEffect(final PsychicTheftCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PsychicTheftCastFromExileEffect copy() {
        return new PsychicTheftCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(objectId)
                && game.getState().getZone(objectId) == Zone.EXILED) {
            Player player = game.getPlayer(source.getControllerId());
            Card card = game.getCard(objectId);
            if (player != null
                    && card != null) {
                return true;
            }
        }
        return false;
    }
}

class SpellWasNotCastCondition implements Condition {

    protected UUID exileId;
    protected UUID cardId;

    public SpellWasNotCastCondition(UUID exileId, UUID cardId) {
        this.exileId = exileId;
        this.cardId = cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getExile().getExileZone(exileId).contains(cardId)) {
            return false;
        }
        SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName(), source.getSourceId());
        if (watcher != null) {
            List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
            if (spells != null) {
                for (Spell spell : spells) {
                    if (spell.getSourceId().equals(cardId)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
