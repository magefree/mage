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
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author LevelX2
 */
public class SilburlindSnapper extends CardImpl {

    public SilburlindSnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add("Turtle");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Silburlind Snapper can't attack unless you've cast a noncreature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SilburlindSnapperEffect()), new SpellsCastWatcher());
    }

    public SilburlindSnapper(final SilburlindSnapper card) {
        super(card);
    }

    @Override
    public SilburlindSnapper copy() {
        return new SilburlindSnapper(this);
    }
}

class SilburlindSnapperEffect extends RestrictionEffect {

    public SilburlindSnapperEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you've cast a noncreature spell this turn";
    }

    public SilburlindSnapperEffect(final SilburlindSnapperEffect effect) {
        super(effect);
    }

    @Override
    public SilburlindSnapperEffect copy() {
        return new SilburlindSnapperEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {

        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
            if (watcher != null) {
                List<Spell> spellsCast = watcher.getSpellsCastThisTurn(source.getControllerId());
                if (spellsCast != null) {
                    for (Spell spell : spellsCast) {
                        if (!spell.isCreature()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
