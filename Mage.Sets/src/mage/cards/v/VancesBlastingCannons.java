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
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.s.SpitfireBastion;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author TheElk801
 */
public class VancesBlastingCannons extends CardImpl {

    public VancesBlastingCannons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.transformable = true;
        this.secondSideCardClazz = SpitfireBastion.class;

        // At the beginning of your upkeep, exile the top card of your library.  If it's a nonland card, you may cast that card this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new VancesBlastingCannonsExileEffect(), TargetController.YOU, false));

        // Whenever you cast your third spell in a turn, transform Vance's Blasting Cannons.
        this.addAbility(new TransformAbility());
        this.addAbility(new VancesBlastingCannonsFlipTrigger(), new CastSpellLastTurnWatcher());
    }

    public VancesBlastingCannons(final VancesBlastingCannons card) {
        super(card);
    }

    @Override
    public VancesBlastingCannons copy() {
        return new VancesBlastingCannons(this);
    }
}

class VancesBlastingCannonsExileEffect extends OneShotEffect {

    public VancesBlastingCannonsExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library.  If it's a nonland card, you may cast that card this turn";
    }

    public VancesBlastingCannonsExileEffect(final VancesBlastingCannonsExileEffect effect) {
        super(effect);
    }

    @Override
    public VancesBlastingCannonsExileEffect copy() {
        return new VancesBlastingCannonsExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be cast the turn it was exiled";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                if (game.getState().getZone(card.getId()) == Zone.EXILED && !card.isLand()) {
                    ContinuousEffect effect = new CastFromNonHandZoneTargetEffect(Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class CastFromNonHandZoneTargetEffect extends AsThoughEffectImpl {

    public CastFromNonHandZoneTargetEffect(Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        staticText = "If it's a nonland card, you may cast that card this turn";
    }

    public CastFromNonHandZoneTargetEffect(final CastFromNonHandZoneTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastFromNonHandZoneTargetEffect copy() {
        return new CastFromNonHandZoneTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (getTargetPointer().getTargets(game, source).contains(objectId)
                && source.getControllerId().equals(affectedControllerId)) {
            Card card = game.getCard(objectId);
            if (card != null) {
                return true;
            }
        }
        return false;
    }
}

class VancesBlastingCannonsFlipTrigger extends TriggeredAbilityImpl {

    public VancesBlastingCannonsFlipTrigger() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect(true));
    }

    public VancesBlastingCannonsFlipTrigger(final VancesBlastingCannonsFlipTrigger ability) {
        super(ability);
    }

    @Override
    public VancesBlastingCannonsFlipTrigger copy() {
        return new VancesBlastingCannonsFlipTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your third spell in a turn, transform {this}";
    }
}
