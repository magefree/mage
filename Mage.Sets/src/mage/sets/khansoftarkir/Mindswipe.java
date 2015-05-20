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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class Mindswipe extends CardImpl {

    public Mindswipe(UUID ownerId) {
        super(ownerId, 189, "Mindswipe", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{U}{R}");
        this.expansionSetCode = "KTK";


        // Counter target spell unless its controller pays {X}.  Mindswipe deals X damage to that spell's controller.
        Effect effect = new CounterUnlessPaysEffect(new ManacostVariableValue());
        effect.setText("Counter target spell unless its controller pays {X}.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new MindswipeEffect());
    }

    public Mindswipe(final Mindswipe card) {
        super(card);
    }

    @Override
    public Mindswipe copy() {
        return new Mindswipe(this);
    }
}

class MindswipeEffect extends OneShotEffect {

    public MindswipeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to that spell's controller";
    }

    public MindswipeEffect(final MindswipeEffect effect) {
        super(effect);
    }

    @Override
    public MindswipeEffect copy() {
        return new MindswipeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject object = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (object == null) {
                object = game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK);
            }
            if (object != null && object instanceof Spell) {
                Spell spell = (Spell) object;
                Player spellController = game.getPlayer(spell.getControllerId());
                if (spellController != null) {
                    int damage = new ManacostVariableValue().calculate(game, source, this);
                    spellController.damage(damage, source.getSourceId(), game, false, true);
                }
                return true;
            }
        }
        return false;
    }
}
