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
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.ThrullToken;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class EndrekSahrMasterBreeder extends CardImpl {

    public EndrekSahrMasterBreeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell, create X 1/1 black Thrull creature tokens, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new EndrekSahrMasterBreederEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false, true));
        // When you control seven or more Thrulls, sacrifice Endrek Sahr, Master Breeder.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterCreaturePermanent("Thrull", "seven or more Thrulls"), ComparisonType.MORE_THAN, 6,
                new SacrificeSourceEffect()));
    }

    public EndrekSahrMasterBreeder(final EndrekSahrMasterBreeder card) {
        super(card);
    }

    @Override
    public EndrekSahrMasterBreeder copy() {
        return new EndrekSahrMasterBreeder(this);
    }
}

class EndrekSahrMasterBreederEffect extends OneShotEffect {

    public EndrekSahrMasterBreederEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X 1/1 black Thrull creature tokens, where X is that spell's converted mana cost";
    }

    public EndrekSahrMasterBreederEffect(final EndrekSahrMasterBreederEffect effect) {
        super(effect);
    }

    @Override
    public EndrekSahrMasterBreederEffect copy() {
        return new EndrekSahrMasterBreederEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(((FixedTarget) getTargetPointer()).getTarget(), Zone.STACK);
        }
        if (spell != null) {
            int cmc = spell.getConvertedManaCost();
            if (cmc > 0) {
                return new CreateTokenEffect(new ThrullToken(), cmc).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
