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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class StoneGiant extends CardImpl {

    public StoneGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        // {tap}: Target creature you control with toughness less than Stone Giant's power gains flying until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new TapSourceCost());
        ability.addTarget(new StoneGiantTarget());
        // Destroy that creature at the beginning of the next end step.
        ability.addEffect(new DestroyTargetAtBeginningOfNextEndStepEffect());
        this.addAbility(ability);
    }

    public StoneGiant(final StoneGiant card) {
        super(card);
    }

    @Override
    public StoneGiant copy() {
        return new StoneGiant(this);
    }
}

class StoneGiantTarget extends TargetPermanent {

    public StoneGiantTarget() {
        super(new FilterControlledCreaturePermanent("creature you control with toughness less than {this}'s power"));
    }

    public StoneGiantTarget(final StoneGiantTarget target) {
        super(target);
    }

    @Override
    public StoneGiantTarget copy() {
        return new StoneGiantTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        Permanent targetCreature = game.getPermanent(id);

        if (targetCreature != null && sourceCreature != null
                && targetCreature.getToughness().getValue() < sourceCreature.getPower().getValue()) {
            return super.canTarget(controllerId, id, source, game);
        }
        return false;
    }
}
