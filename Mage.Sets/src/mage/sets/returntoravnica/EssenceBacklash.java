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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class EssenceBacklash extends CardImpl<EssenceBacklash> {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public EssenceBacklash(UUID ownerId) {
        super(ownerId, 160, "Essence Backlash", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);
        this.color.setRed(true);

        // Counter target creature spell. Essence Backlash deals damage equal to that spell's power to its controller.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new EssenceBacklashEffect());
    }

    public EssenceBacklash(final EssenceBacklash card) {
        super(card);
    }

    @Override
    public EssenceBacklash copy() {
        return new EssenceBacklash(this);
    }
}

class EssenceBacklashEffect extends OneShotEffect<EssenceBacklashEffect> {

    public EssenceBacklashEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Counter target creature spell. Essence Backlash deals damage equal to that spell's power to its controller";
    }

    public EssenceBacklashEffect(final EssenceBacklashEffect effect) {
        super(effect);
    }

    @Override
    public EssenceBacklashEffect copy() {
        return new EssenceBacklashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Boolean result = false;
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player spellController = game.getPlayer(spell.getControllerId());

            result = game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
            if (spellController != null) {
                spellController.damage(spell.getPower().getValue(), source.getSourceId(), game, false, true);
            }
        }
        return result;
    }
}
