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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAndDealtDamageThisTurnTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;



/**
 * @author noxx
 */
public class DreadSlaver extends CardImpl<DreadSlaver> {

    public DreadSlaver(UUID ownerId) {
        super(ownerId, 98, "Dread Slaver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Zombie");
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever a creature dealt damage by Dread Slaver this turn dies, return it to the battlefield under your control. That creature is a black Zombie in addition to its other colors and types.
        this.addAbility(new DiesAndDealtDamageThisTurnTriggeredAbility(new DreadSlaverEffect(), false));
    }

    public DreadSlaver(final DreadSlaver card) {
        super(card);
    }

    @Override
    public DreadSlaver copy() {
        return new DreadSlaver(this);
    }
}

class DreadSlaverEffect extends OneShotEffect<DreadSlaverEffect> {

    public DreadSlaverEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield under your control. That creature is a black Zombie in addition to its other colors and types";
    }

    public DreadSlaverEffect(final DreadSlaverEffect effect) {
        super(effect);
    }

    @Override
    public DreadSlaverEffect copy() {
        return new DreadSlaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            Zone currentZone = game.getState().getZone(card.getId());
            if (card.putOntoBattlefield(game, currentZone, source.getSourceId(), source.getControllerId())) {
                ContinuousEffect effect = new DreadSlaverContiniousEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

}

class DreadSlaverContiniousEffect extends ContinuousEffectImpl<DreadSlaverContiniousEffect> {

    public DreadSlaverContiniousEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "That creature is a black Zombie in addition to its other colors and types";
    }

    public DreadSlaverContiniousEffect(final DreadSlaverContiniousEffect effect) {
        super(effect);
    }

    @Override
    public DreadSlaverContiniousEffect copy() {
        return new DreadSlaverContiniousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        creature.getSubtype().add("Zombie");
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        creature.getColor().setBlack(true);
                    }
                    break;
            }
            return true;
        } else {
            this.used = true;
        }
        return false;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
