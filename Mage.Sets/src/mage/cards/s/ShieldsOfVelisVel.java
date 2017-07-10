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

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class ShieldsOfVelisVel extends CardImpl {

    public ShieldsOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{W}");
        this.subtype.add("Shapeshifter");

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());

        //Creatures target player controls get +0/+1 and gain all creature types until end of turn.
        this.getSpellAbility().addEffect(new ShieldsOfVelisVelBoostEffect());
        this.getSpellAbility().addEffect(new ShieldsOfVelisVelGainEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public ShieldsOfVelisVel(final ShieldsOfVelisVel card) {
        super(card);
    }

    @Override
    public ShieldsOfVelisVel copy() {
        return new ShieldsOfVelisVel(this);
    }
}

class ShieldsOfVelisVelGainEffect extends ContinuousEffectImpl {

    public ShieldsOfVelisVelGainEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "and gain all creature types until end of turn";
    }

    public ShieldsOfVelisVelGainEffect(final ShieldsOfVelisVelGainEffect effect) {
        super(effect);
    }

    @Override
    public ShieldsOfVelisVelGainEffect copy() {
        return new ShieldsOfVelisVelGainEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addAbility(ChangelingAbility.getInstance(), source.getSourceId(), game, false);
            } else {
                it.remove();
            }
        }
        return true;
    }
}

class ShieldsOfVelisVelBoostEffect extends ContinuousEffectImpl {

    public ShieldsOfVelisVelBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures target player controls get +0/+1";
    }

    public ShieldsOfVelisVelBoostEffect(final ShieldsOfVelisVelBoostEffect effect) {
        super(effect);
    }

    @Override
    public ShieldsOfVelisVelBoostEffect copy() {
        return new ShieldsOfVelisVelBoostEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addToughness(1);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
