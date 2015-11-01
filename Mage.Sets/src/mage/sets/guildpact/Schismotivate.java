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
package mage.sets.guildpact;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class Schismotivate extends CardImpl {

    public Schismotivate(UUID ownerId) {
        super(ownerId, 129, "Schismotivate", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");
        this.expansionSetCode = "GPT";

        // Target creature gets +4/+0 until end of turn. Another target creature gets -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new SchismotivateEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    public Schismotivate(final Schismotivate card) {
        super(card);
    }

    @Override
    public Schismotivate copy() {
        return new Schismotivate(this);
    }
}

class SchismotivateEffect extends ContinuousEffectImpl {

    public SchismotivateEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Target creature gets +4/+0 until end of turn. Another target creature gets -4/-0 until end of turn";
    }

    public SchismotivateEffect(final SchismotivateEffect effect) {
        super(effect);
    }

    @Override
    public SchismotivateEffect copy() {
        return new SchismotivateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addPower(4);
        }
        permanent = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        if (permanent != null) {
            permanent.addPower(-4);
        }
        return true;
    }
}