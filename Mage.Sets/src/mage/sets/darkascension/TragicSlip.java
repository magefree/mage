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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class TragicSlip extends CardImpl<TragicSlip> {

    public TragicSlip(UUID ownerId) {
        super(ownerId, 76, "Tragic Slip", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "DKA";

        this.color.setBlack(true);

        // Target creature gets -1/-1 until end of turn.
        // Morbid - That creature gets -13/-13 until end of turn instead if a creature died this turn.
        this.getSpellAbility().addEffect(new TragicSlipEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TragicSlip(final TragicSlip card) {
        super(card);
    }

    @Override
    public TragicSlip copy() {
        return new TragicSlip(this);
    }
}

class TragicSlipEffect extends ContinuousEffectImpl<TragicSlipEffect> {

    private ContinuousEffect effect;
    private ContinuousEffect otherwiseEffect;
    private Condition condition;

    public TragicSlipEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.effect = new BoostTargetEffect(-13, -13, Duration.EndOfTurn);
        this.otherwiseEffect = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
        this.condition = MorbidCondition.getInstance();
        this.staticText = "Target creature gets -1/-1 until end of turn. Morbid - That creature gets -13/-13 until end of turn instead if a creature died this turn.";
    }

    public TragicSlipEffect(final TragicSlipEffect effect) {
        super(effect);
        this.effect = effect.effect;
        this.otherwiseEffect = effect.otherwiseEffect;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (condition.apply(game, source)) {
            return effect.apply(layer, sublayer, source, game);
        } else {
            return otherwiseEffect.apply(layer, sublayer, source, game);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (condition.apply(game, source)) {
            return effect.apply(game, source);
        } else {
            return otherwiseEffect.apply(game, source);
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return effect.hasLayer(layer);
    }

    @Override
    public TragicSlipEffect copy() {
        return new TragicSlipEffect(this);
    }
}