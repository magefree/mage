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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public class KaradorGhostChieftain extends CardImpl {

    public KaradorGhostChieftain(UUID ownerId) {
        super(ownerId, 207, "Karador, Ghost Chieftain", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{B}{G}{W}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Centaur");
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KaradorGhostChieftainCostReductionEffect()));
        
        // During each of your turns, you may cast one creature card from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KaradorGhostChieftainContinuousEffect()), new KaradorGhostChieftainWatcher());
    }

    public KaradorGhostChieftain(final KaradorGhostChieftain card) {
        super(card);
    }

    @Override
    public KaradorGhostChieftain copy() {
        return new KaradorGhostChieftain(this);
    }
}

class KaradorGhostChieftainCostReductionEffect extends CostModificationEffectImpl {

    KaradorGhostChieftainCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each creature card in your graveyard";
    }

    KaradorGhostChieftainCostReductionEffect(KaradorGhostChieftainCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int reductionAmount = player.getGraveyard().count(new FilterCreatureCard(), game);
            CardUtil.reduceCost(abilityToModify, reductionAmount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public KaradorGhostChieftainCostReductionEffect copy() {
        return new KaradorGhostChieftainCostReductionEffect(this);
    }
}

class KaradorGhostChieftainContinuousEffect extends ContinuousEffectImpl {

    KaradorGhostChieftainContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "During each of your turns, you may cast one creature card from your graveyard";
    }

    KaradorGhostChieftainContinuousEffect(final KaradorGhostChieftainContinuousEffect effect) {
        super(effect);
    }

    @Override
    public KaradorGhostChieftainContinuousEffect copy() {
        return new KaradorGhostChieftainContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getGraveyard().getCards(new FilterCreatureCard(), game)) {
                ContinuousEffect effect = new KaradorGhostChieftainCastFromGraveyardEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class KaradorGhostChieftainCastFromGraveyardEffect extends AsThoughEffectImpl {

    KaradorGhostChieftainCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast one creature card from your graveyard";
    }

    KaradorGhostChieftainCastFromGraveyardEffect(final KaradorGhostChieftainCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KaradorGhostChieftainCastFromGraveyardEffect copy() {
        return new KaradorGhostChieftainCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                KaradorGhostChieftainWatcher watcher = (KaradorGhostChieftainWatcher) game.getState().getWatchers().get("KaradorGhostChieftainWatcher", source.getSourceId());
                return !watcher.isAbilityUsed();
            }
        }
        return false;
    }
}

class KaradorGhostChieftainWatcher extends Watcher {

    boolean abilityUsed = false;

    KaradorGhostChieftainWatcher() {
        super("KaradorGhostChieftainWatcher", WatcherScope.CARD);
    }

    KaradorGhostChieftainWatcher(final KaradorGhostChieftainWatcher watcher) {
        super(watcher);
        this.abilityUsed = watcher.abilityUsed;
    }

    @Override
    public void watch(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.getCardType().contains(CardType.CREATURE)) {
               abilityUsed = true;
            }
        }
    }

    @Override
    public KaradorGhostChieftainWatcher copy() {
        return new KaradorGhostChieftainWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsed = false;
    }

    public boolean isAbilityUsed() {
        return abilityUsed;
    }
}