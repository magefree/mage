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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public class CavernsOfDespair extends CardImpl {

    public CavernsOfDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        this.addSuperType(SuperType.WORLD);

        // No more than two creatures can attack each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CavernsOfDespairAttackRestrictionEffect()));

        // No more than two creatures can block each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CavernsOfDespairBlockRestrictionEffect()));
    }

    public CavernsOfDespair(final CavernsOfDespair card) {
        super(card);
    }

    @Override
    public CavernsOfDespair copy() {
        return new CavernsOfDespair(this);
    }
}

class CavernsOfDespairAttackRestrictionEffect extends RestrictionEffect {

    public CavernsOfDespairAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than two creatures can attack each combat";
    }

    public CavernsOfDespairAttackRestrictionEffect(final CavernsOfDespairAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public CavernsOfDespairAttackRestrictionEffect copy() {
        return new CavernsOfDespairAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        return game.getCombat().getAttackers().size() < 2;
    }
}

class CavernsOfDespairBlockRestrictionEffect extends RestrictionEffect {

    public CavernsOfDespairBlockRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than two creatures can block each combat";
    }

    public CavernsOfDespairBlockRestrictionEffect(final CavernsOfDespairBlockRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public CavernsOfDespairBlockRestrictionEffect copy() {
        return new CavernsOfDespairBlockRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return game.getCombat().getBlockers().size() < 2;
    }
}
