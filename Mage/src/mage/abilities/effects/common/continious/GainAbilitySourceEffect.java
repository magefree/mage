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
package mage.abilities.effects.common.continious;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilitySourceEffect extends ContinuousEffectImpl<GainAbilitySourceEffect> implements SourceEffect {

    protected Ability ability;
    // shall a card gain the ability (otherwise permanent)
    private boolean onCard;

    /**
     * Add ability with Duration.WhileOnBattlefield
     *
     * @param ability
     */
    public GainAbilitySourceEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration) {
        this(ability, duration, false);
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration, boolean onCard) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = "{this} gains \"" + ability.getRule() + "\" " + duration.toString();
        this.onCard = onCard;
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration, boolean onCard, boolean noStaticText) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.onCard = onCard;
        if (noStaticText) {
            staticText = null;
        }
    }

    public GainAbilitySourceEffect(final GainAbilitySourceEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.onCard = effect.onCard;
    }

    @Override
    public GainAbilitySourceEffect copy() {
        return new GainAbilitySourceEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        getAffectedObjects().add(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (onCard) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                // add ability to card only once
                card.addAbility(ability);
                discard();
                return true;
            }
        } else {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.addAbility(ability, source.getSourceId(), game);
                return true;
            }
            if (duration.equals(Duration.Custom)) {
                this.discard();
            }            
        }
        return false;
    }
}
