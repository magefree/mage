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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * Once you announce you’re casting Rescue from the Underworld, no player may attempt to
 * stop you from casting the spell by removing the creature you want to sacrifice.
 *
 * If you sacrifice a creature token to cast Rescue from the Underworld, it won’t return
 * to the battlefield, although the target creature card will.
 *
 * If either the sacrificed creature or the target creature card leaves the graveyard
 * before the delayed triggered ability resolves during your next upkeep, it won’t return.
 *
 * However, if the sacrificed creature is put into another public zone instead of the graveyard,
 * perhaps because it’s your commander or because of another replacement effect, it will return
 * to the battlefield from the zone it went to.
 *
 * Rescue from the Underworld is exiled as it resolves, not later as its delayed trigger resolves.
 *
 *
 * @author LevelX2
 */
public class RescueFromTheUnderworld extends CardImpl<RescueFromTheUnderworld> {

    public RescueFromTheUnderworld(UUID ownerId) {
        super(ownerId, 102, "Rescue from the Underworld", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{B}");
        this.expansionSetCode = "THS";

        this.color.setBlack(true);

        // As an additional cost to cast Rescue from the Underworld, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,new FilterControlledCreaturePermanent("a creature"), false)));

        // Choose target creature card in your graveyard. Return that card and the sacrificed card to the battlefield under your control at the beginning of your next upkeep. Exile Rescue from the Underworld.
        this.getSpellAbility().addEffect(new RescueFromTheUnderworldTextEffect());
        this.getSpellAbility().addEffect(new RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(new RescueFromTheUnderworldDelayedTriggeredAbility()));
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public RescueFromTheUnderworld(final RescueFromTheUnderworld card) {
        super(card);
    }

    @Override
    public RescueFromTheUnderworld copy() {
        return new RescueFromTheUnderworld(this);
    }
}

class RescueFromTheUnderworldTextEffect extends OneShotEffect<RescueFromTheUnderworldTextEffect> {

    public RescueFromTheUnderworldTextEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature card in your graveyard";
    }

    public RescueFromTheUnderworldTextEffect(final RescueFromTheUnderworldTextEffect effect) {
        super(effect);
    }

    @Override
    public RescueFromTheUnderworldTextEffect copy() {
        return new RescueFromTheUnderworldTextEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect extends OneShotEffect<RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect> {

    protected DelayedTriggeredAbility ability;

    public RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability) {
        super(ability.getEffects().get(0).getOutcome());
        this.ability = ability;
    }

    public RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(final RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect copy() {
        return new RescueFromTheUnderworldCreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = (DelayedTriggeredAbility) ability.copy();
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        delayedAbility.getTargets().addAll(source.getTargets());
        for(Effect effect : delayedAbility.getEffects()) {
            effect.getTargetPointer().init(game, source);
        }
        // add the sacrificed creature as target
        for (Cost cost :source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
                TargetCardInGraveyard target = new TargetCardInGraveyard();
                for(Permanent permanent : sacCost.getPermanents()) {
                    target.add(permanent.getId(), game);
                    delayedAbility.getTargets().add(target);
                }
            }
        }

        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return ability.getRule();
    }

}

class RescueFromTheUnderworldDelayedTriggeredAbility extends DelayedTriggeredAbility<RescueFromTheUnderworldDelayedTriggeredAbility> {

    public RescueFromTheUnderworldDelayedTriggeredAbility() {
        this(new RescueFromTheUnderworldReturnEffect(), TargetController.YOU);
    }

    public RescueFromTheUnderworldDelayedTriggeredAbility(Effect effect, TargetController targetController) {
        super(effect);
    }

    public RescueFromTheUnderworldDelayedTriggeredAbility(RescueFromTheUnderworldDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public RescueFromTheUnderworldDelayedTriggeredAbility copy() {
        return new RescueFromTheUnderworldDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Return that card and the sacrificed card to the battlefield under your control at the beginning of your next upkeep";
    }
}

class RescueFromTheUnderworldReturnEffect extends OneShotEffect<RescueFromTheUnderworldReturnEffect> {

    private boolean tapped;

    public RescueFromTheUnderworldReturnEffect() {
        this(false);
    }

    public RescueFromTheUnderworldReturnEffect(boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
    }

    public RescueFromTheUnderworldReturnEffect(final RescueFromTheUnderworldReturnEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
    }

    @Override
    public RescueFromTheUnderworldReturnEffect copy() {
        return new RescueFromTheUnderworldReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        // Target card comes only back if in graveyard
        for (UUID targetId: getTargetPointer().getTargets(game, source)) {
            Card card = game.getCard(targetId);
            if (card != null) {
                Player player = game.getPlayer(card.getOwnerId());
                if (player != null) {
                    if(card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId(), tapped)){
                        result = true;
                    }
                }
            }
        }
        // However, if the sacrificed creature is put into another public zone instead of the graveyard,
        // perhaps because it’s your commander or because of another replacement effect, it will return
        // to the battlefield from the zone it went to.
        if (source.getTargets().get(1) != null) {
            for (UUID targetId: ((Target) source.getTargets().get(1)).getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null && !card.isFaceDown()) {
                    Player player = game.getPlayer(card.getOwnerId());
                    if (player != null) {
                        Zone currentZone = game.getState().getZone(card.getId());
                        if (currentZone.equals(Zone.COMMAND) || currentZone.equals(Zone.GRAVEYARD) || currentZone.equals(Zone.EXILED)) {
                            if(card.putOntoBattlefield(game, currentZone, source.getSourceId(), source.getControllerId(), false)){
                                result = true;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

}
