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

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class CoatOfArms extends CardImpl {

    public CoatOfArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CoatOfArmsEffect()));
    }

    public CoatOfArms(final CoatOfArms card) {
        super(card);
    }

    @Override
    public CoatOfArms copy() {
        return new CoatOfArms(this);
    }
}

class CoatOfArmsEffect extends ContinuousEffectImpl {

   public CoatOfArmsEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it";
    }

    public CoatOfArmsEffect(final CoatOfArmsEffect effect) {
        super(effect);
    }

    @Override
    public CoatOfArmsEffect copy() {
        return new CoatOfArmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            int amount = getAmount(permanents, permanent, game);
            permanent.addPower(amount);
            permanent.addToughness(amount);
        }
        return true;
    }

    private int getAmount(List<Permanent> permanents, Permanent target, Game game) {
        int amount = 0;
        List<String> targetSubtype = target.getSubtype(game);
        if (target.getAbilities().contains(ChangelingAbility.getInstance())) {
            return permanents.size() - 1;
        }
        for (Permanent permanent : permanents) {
            if (!permanent.getId().equals(target.getId())) {
                for (String subtype : targetSubtype) {
                    if (!CardUtil.isNonCreatureSubtype(subtype)) {
                        if (permanent.hasSubtype(subtype, game)) {
                            amount++;
                            break;
                        }
                    }
                }
            }
        }
        return amount;
    }
}
