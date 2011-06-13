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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class SignalPest extends CardImpl<SignalPest> {

    public SignalPest(UUID ownerId) {
        super(ownerId, 131, "Signal Pest", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Pest");

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        this.addAbility(new BattleCryAbility());
        this.addAbility(new SignalPestAbility());
    }

    public SignalPest(final SignalPest card) {
        super(card);
    }

    @Override
    public SignalPest copy() {
        return new SignalPest(this);
    }
}

class SignalPestAbility extends EvasionAbility<SignalPestAbility> {

    private static SignalPestAbility instance;

    public static SignalPestAbility getInstance() {
        if (instance == null) {
            instance = new SignalPestAbility();
        }
        return instance;
    }

    public SignalPestAbility() {
        this.addEffect(new SignalPestEffect());
    }

    @Override
    public String getRule() {
        return "Signal Pest can't be blocked except by creatures with flying or reach";
    }

    @Override
    public SignalPestAbility copy() {
        return getInstance();
    }
}

class SignalPestEffect extends RestrictionEffect<SignalPestEffect> {

    public SignalPestEffect() {
        super(Constants.Duration.WhileOnBattlefield);
    }

    public SignalPestEffect(final SignalPestEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(SignalPestAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Game game) {
        if (blocker.getAbilities().contains(FlyingAbility.getInstance()) || blocker.getAbilities().contains(ReachAbility.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    public SignalPestEffect copy() {
        return new SignalPestEffect(this);
    }
}