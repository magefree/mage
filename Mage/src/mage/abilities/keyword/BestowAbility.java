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

package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.constants.Zone;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.SourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 *
 * Bestow
 * FIXME: Add offical rulings
 *
 *
 * @author LevelX2
 */


public class BestowAbility extends SpellAbility {

    public BestowAbility(Card card, String manaString) {
        super(new ManaCostsImpl(manaString), card.getName() + " using bestow");
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.addTarget(auraTarget);
        this.addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BestowTypeChangingEffect());
        ability.setRuleVisible(false);
        card.addAbility(ability);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        boolean result;
        result = super.activate(game, noMana);
        if (result) {
            Spell spell = game.getStack().getSpell(this.getOriginalId());
            if (spell != null) {
                spell.getSubtype().add("Aura");
            }
        }
        return result;
    }

    public BestowAbility(final BestowAbility ability) {
        super(ability);
    }

    @Override
    public BestowAbility copy() {
        return new BestowAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Bestow " + getManaCostsToPay().getText()+ " <i>(If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)</i>";
    }

}

class BestowTypeChangingEffect extends ContinuousEffectImpl<BestowTypeChangingEffect> implements SourceEffect {

    public BestowTypeChangingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
    }

    public BestowTypeChangingEffect(final BestowTypeChangingEffect effect) {
        super(effect);
    }

    @Override
    public BestowTypeChangingEffect copy() {
        return new BestowTypeChangingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (permanent.getAttachedTo() == null) {
                        if (permanent.getSubtype().contains("Aura")) {
                            permanent.getSubtype().remove("Aura");
                        }
                    } else {
                        if (sublayer == SubLayer.NA) {
                            permanent.getCardType().remove(CardType.CREATURE);
                        }
                        if (!permanent.getSubtype().contains("Aura")) {
                            permanent.getSubtype().add("Aura");
                        }
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }


    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

}
