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
package mage.sets.conspiracy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author emerald000
 */
public class DackFayden extends CardImpl {

    public DackFayden(UUID ownerId) {
        super(ownerId, 42, "Dack Fayden", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}");
        this.expansionSetCode = "CNS";
        this.subtype.add("Dack");

        this.color.setBlue(true);
        this.color.setRed(true);
        
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +1: Target player draws two cards, then discards two cards.
        LoyaltyAbility ability = new LoyaltyAbility(new DrawCardTargetEffect(2), 1);
        Effect effect = new DiscardTargetEffect(2);
        effect.setText(", then discards two cards");
        ability.addEffect(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
        // -2: Gain control of target artifact.
        effect = new GainControlTargetEffect(Duration.EndOfGame);
        effect.setText("Gain control of target artifact");
        ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
        
        // -6: You get an emblem with "Whenever you cast a spell that targets one or more permanents, gain control of those permanents."
        effect = new GetEmblemEffect(new DackFaydenEmblem());
        effect.setText("You get an emblem with \"Whenever you cast a spell that targets one or more permanents, gain control of those permanents.\"");
        ability = new LoyaltyAbility(effect, -6);
        this.addAbility(ability);
    }

    public DackFayden(final DackFayden card) {
        super(card);
    }

    @Override
    public DackFayden copy() {
        return new DackFayden(this);
    }
}

class DackFaydenEmblem extends Emblem {
    
    DackFaydenEmblem() {
        this.setName("EMBLEM: Dack Fayden");
        this.getAbilities().add(new DackFaydenEmblemTriggeredAbility());
    }
}

class DackFaydenEmblemTriggeredAbility extends TriggeredAbilityImpl {
    
    DackFaydenEmblemTriggeredAbility() {
        super(Zone.COMMAND, new DackFaydenEmblemEffect(), false);
    }
    
    DackFaydenEmblemTriggeredAbility(final DackFaydenEmblemTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public DackFaydenEmblemTriggeredAbility copy() {
        return new DackFaydenEmblemTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean returnValue = false;
        List<UUID> targettedPermanents = new ArrayList<>(0);
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    SpellAbility spellAbility = spell.getSpellAbility();
                    for (Target target : spellAbility.getTargets()) {
                        if (!target.isNotTarget()) {
                            for (UUID targetId : target.getTargets()) {
                                if (game.getBattlefield().containsPermanent(targetId)) {
                                    returnValue = true;
                                    targettedPermanents.add(targetId);
                                }
                            }
                        }
                    }
                    for (Effect effect : spellAbility.getEffects()) {
                        for (UUID targetId : effect.getTargetPointer().getTargets(game, spellAbility)) {
                            if (game.getBattlefield().containsPermanent(targetId)) {
                                returnValue = true;
                                targettedPermanents.add(targetId);
                            }
                        }
                    }
                }
            }
        }
        for (Effect effect : this.getEffects()) {
            if (effect instanceof DackFaydenEmblemEffect) {
                DackFaydenEmblemEffect dackEffect = (DackFaydenEmblemEffect) effect;
                dackEffect.setPermanents(targettedPermanents);
            }
        }
        return returnValue;
    }
    
    @Override
    public String getRule() {
        return "Whenever you cast a spell that targets one or more permanents, gain control of those permanents.";
    }
}

class DackFaydenEmblemEffect extends ContinuousEffectImpl {
    
    protected List<UUID> permanents;
    
    DackFaydenEmblemEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "gain control of those permanents";
    }
    
    DackFaydenEmblemEffect(final DackFaydenEmblemEffect effect) {
        super(effect);
        this.permanents = effect.permanents;
    }
    
    @Override
    public DackFaydenEmblemEffect copy() {
        return new DackFaydenEmblemEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : this.permanents) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.changeControllerId(source.getControllerId(), game);
            }
        }
        return true;
    }
    
    public void setPermanents(List<UUID> targettedPermanents) {
        this.permanents = new ArrayList<>(targettedPermanents);
    }
}
