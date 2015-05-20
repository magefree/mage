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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DiabolicServitude extends CardImpl {

    public DiabolicServitude(UUID ownerId) {
        super(ownerId, 130, "Diabolic Servitude", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "USG";


        // When Diabolic Servitude enters the battlefield, return target creature card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiabolicServitudeReturnCreatureEffect());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.addAbility(ability);

        // When the creature put onto the battlefield with Diabolic Servitude dies, exile it and return Diabolic Servitude to its owner's hand.
        this.addAbility(new DiabolicServitudeCreatureDiesTriggeredAbility());
        // When Diabolic Servitude leaves the battlefield, exile the creature put onto the battlefield with Diabolic Servitude.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DiabolicServitudeSourceLeftBattlefieldEffect(), false));

    }

    public DiabolicServitude(final DiabolicServitude card) {
        super(card);
    }

    @Override
    public DiabolicServitude copy() {
        return new DiabolicServitude(this);
    }
}

class DiabolicServitudeReturnCreatureEffect extends OneShotEffect {

    public DiabolicServitudeReturnCreatureEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield";
    }

    public DiabolicServitudeReturnCreatureEffect(final DiabolicServitudeReturnCreatureEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicServitudeReturnCreatureEffect copy() {
        return new DiabolicServitudeReturnCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card cardInGraveyard = game.getCard(getTargetPointer().getFirst(game, source));
        if (cardInGraveyard != null) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(getTargetPointer());
            effect.apply(game, source);

            game.getState().setValue(source.getSourceId().toString() + "returnedCreature", new MageObjectReference(cardInGraveyard.getId(), game));

            return true;
        }
        return false;
    }
}

class DiabolicServitudeCreatureDiesTriggeredAbility extends TriggeredAbilityImpl {

    public DiabolicServitudeCreatureDiesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiabolicServitudeExileCreatureEffect(), false);
    }

    public DiabolicServitudeCreatureDiesTriggeredAbility(final DiabolicServitudeCreatureDiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiabolicServitudeCreatureDiesTriggeredAbility copy() {
        return new DiabolicServitudeCreatureDiesTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Object object = game.getState().getValue(getSourceId().toString() + "returnedCreature");
            if ((object instanceof MageObjectReference) && ((MageObjectReference)object).refersTo(zEvent.getTarget(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When the creature put onto the battlefield with {this} dies, exile it and return {this} to its owner's hand.";
    }
}

class DiabolicServitudeExileCreatureEffect extends OneShotEffect {

    public DiabolicServitudeExileCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it and return {this} to its owner's hand";
    }

    public DiabolicServitudeExileCreatureEffect(final DiabolicServitudeExileCreatureEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicServitudeExileCreatureEffect copy() {
        return new DiabolicServitudeExileCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(source.getSourceId().toString() + "returnedCreature");
        if ((object instanceof MageObjectReference)) {
            Effect effect = new ExileTargetEffect();
            effect.setTargetPointer(new FixedTarget(((MageObjectReference)object).getSourceId()));
            effect.apply(game, source);
            return new ReturnToHandSourceEffect(true).apply(game, source);
        }
        return false;
    }
}

class DiabolicServitudeSourceLeftBattlefieldEffect extends OneShotEffect {

    public DiabolicServitudeSourceLeftBattlefieldEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the creature put onto the battlefield with {this}";
    }

    public DiabolicServitudeSourceLeftBattlefieldEffect(final DiabolicServitudeSourceLeftBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicServitudeSourceLeftBattlefieldEffect copy() {
        return new DiabolicServitudeSourceLeftBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(source.getSourceId().toString() + "returnedCreature");
        if ((object instanceof MageObjectReference)) {
            Effect effect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
            effect.setTargetPointer(new FixedTarget(((MageObjectReference)object).getSourceId()));
            effect.apply(game, source);
        }
        return false;
    }
}
