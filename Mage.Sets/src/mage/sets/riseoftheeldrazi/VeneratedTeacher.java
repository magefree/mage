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
package mage.sets.riseoftheeldrazi;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.Constants.Outcome;
import mage.counters.CounterType;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.abilities.keyword.LevelUpAbility;

/**
 *
 * @author jeffwadsworth
 */
public class VeneratedTeacher extends CardImpl<VeneratedTeacher> {

    public VeneratedTeacher(UUID ownerId) {
        super(ownerId, 93, "Venerated Teacher", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Venerated Teacher enters the battlefield, put two level counters on each creature you control with level up.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VeneratedTeacherEffect()));
    }

    public VeneratedTeacher(final VeneratedTeacher card) {
        super(card);
    }

    @Override
    public VeneratedTeacher copy() {
        return new VeneratedTeacher(this);
    }
}

class VeneratedTeacherEffect extends OneShotEffect<VeneratedTeacherEffect> {
    
    FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control");
    
    public VeneratedTeacherEffect() {
        super(Outcome.BoostCreature);
        staticText = "put two level counters on each creature you control with level up";
    }
    
    public VeneratedTeacherEffect(final VeneratedTeacherEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId());
        if (!permanents.isEmpty()) {
            for (Permanent permanent : permanents) {
                for (Ability ability : permanent.getAbilities()) {
                    if (ability instanceof LevelUpAbility) {
                        permanent.addCounters(CounterType.LEVEL.createInstance(2), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public VeneratedTeacherEffect copy() {
        return new VeneratedTeacherEffect(this);
    }
}