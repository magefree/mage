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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class RefuseCooperate extends SplitCard {

    public RefuseCooperate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{3}{R}", "{2}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Refuse
        // Refuse deals damage to target spell's controller equal to that spell's converted mana cost.
        getLeftHalfCard().getSpellAbility().addEffect(new RefuseEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());

        // Cooperate
        // Aftermath
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        getRightHalfCard().getSpellAbility().addEffect(new CopyTargetSpellEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetSpell(new FilterInstantOrSorcerySpell()));
    }

    public RefuseCooperate(final RefuseCooperate card) {
        super(card);
    }

    @Override
    public RefuseCooperate copy() {
        return new RefuseCooperate(this);
    }
}

class RefuseEffect extends OneShotEffect {

    public RefuseEffect() {
        super(Outcome.Damage);
        staticText = "Refuse deals damage to target spell's controller equal to that spell's converted mana cost";
    }

    public RefuseEffect(final RefuseEffect effect) {
        super(effect);
    }

    @Override
    public RefuseEffect copy() {
        return new RefuseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getFirstTarget());
            if (spell != null) {
                Player spellController = game.getPlayer(spell.getControllerId());
                if (spellController != null) {
                    spellController.damage(spell.getConvertedManaCost(), source.getSourceId(), game, false, true);
                    return true;
                }
            }
        }
        return false;
    }
}
