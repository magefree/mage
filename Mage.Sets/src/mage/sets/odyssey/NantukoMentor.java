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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public class NantukoMentor extends CardImpl<NantukoMentor> {

    public NantukoMentor(UUID ownerId) {
        super(ownerId, 255, "Nantuko Mentor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Insect");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}, {tap}: Target creature gets +X/+X until end of turn, where X is that creature's power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NantukoMentorBoostTargetEffect(), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }

    public NantukoMentor(final NantukoMentor card) {
        super(card);
    }

    @Override
    public NantukoMentor copy() {
        return new NantukoMentor(this);
    }
}

class NantukoMentorBoostTargetEffect extends ContinuousEffectImpl<NantukoMentorBoostTargetEffect> {

public NantukoMentorBoostTargetEffect() {
    super(Duration.EndOfTurn, Outcome.BoostCreature);
    staticText = "Target creature gets +X/+X until end of turn, where X is that creature's power.";
}

public NantukoMentorBoostTargetEffect(final NantukoMentorBoostTargetEffect effect) {
    super(effect);
}

@Override
public NantukoMentorBoostTargetEffect copy() {
    return new NantukoMentorBoostTargetEffect(this);
}

@Override
public boolean apply(Game game, Ability source){
    Permanent target = game.getPermanent(source.getFirstTarget());
    if (target != null) {
        MageInt power = target.getPower();
        target.addPower(power.getValue());
        target.addToughness(power.getValue());
    }
    return true;
}

}