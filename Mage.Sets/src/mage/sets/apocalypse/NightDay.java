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

package mage.sets.apocalypse;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.cards.SplitCard;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */

public class NightDay extends SplitCard<NightDay> {

    public NightDay(UUID ownerId) {
        super(ownerId, 131, "Night", "Day", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}", "{2}{W}", false);
        this.expansionSetCode = "APC";

        this.color.setBlack(true);
        this.color.setWhite(true);

        // Night
        // Target creature gets -1/-1 until end of turn.
        getLeftHalfCard().getColor().setBlack(true);
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-1,-1,Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(true));

        // Day
        // Creatures target player controls get +1/+1 until end of turn.
        getRightHalfCard().getColor().setWhite(true);
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer(true));
        getRightHalfCard().getSpellAbility().addEffect(new DayEffect());

    }

    public NightDay(final NightDay card) {
        super(card);
    }

    @Override
    public NightDay copy() {
        return new NightDay(this);
    }
}

class DayEffect extends ContinuousEffectImpl<DayEffect> {

    public DayEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures target player controls get +1/+1 until end of turn";
    }

    public DayEffect(final DayEffect effect) {
        super(effect);
    }

    @Override
    public DayEffect copy() {
        return new DayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                objects.add(creature.getId());
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getFirstTarget(), game);
        for (Permanent creature : creatures) {
            if (!this.affectedObjectsSet || objects.contains(creature.getId())) {
                creature.addPower(1);
                creature.addToughness(1);
            }
        }
        return true;
    }
}
