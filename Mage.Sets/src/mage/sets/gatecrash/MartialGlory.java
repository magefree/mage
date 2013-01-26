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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class MartialGlory extends CardImpl<MartialGlory> {

    public MartialGlory(UUID ownerId) {
        super(ownerId, 175, "Martial Glory", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}{W}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);
        this.color.setWhite(true);

        // Target creature gets +3/+0 until end of turn.
        Effect effect = new BoostTargetEffect(3,0, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+0 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("first creature")));
        
        // Target creature gets +0/+3 until end of turn.
        Effect effect2 = new BoostTargetEffect(0,3, Duration.EndOfTurn);
        effect2.setText("<br></br>Target creature gets +0/+3 until end of turn");
        effect2.setTargetPointer(SecondTargetPointer.getInstance());
        this.getSpellAbility().addEffect(effect2);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)")));
        
    }

    public MartialGlory(final MartialGlory card) {
        super(card);
    }

    @Override
    public MartialGlory copy() {
        return new MartialGlory(this);
    }
}
class MartialGloryEffect1 extends OneShotEffect<MartialGloryEffect1> {

    public MartialGloryEffect1() {
        super(Outcome.BoostCreature);
        staticText = "Target creature gets +3/+0 until end of turn.";
    }

    public MartialGloryEffect1(final MartialGloryEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ContinuousEffect effect = new BoostTargetEffect(3,0, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(source.getTargets().get(0).getFirstTarget()));
        game.addEffect(effect, source);
        return true;
    }

    @Override
    public MartialGloryEffect1 copy() {
        return new MartialGloryEffect1(this);
    }
}
