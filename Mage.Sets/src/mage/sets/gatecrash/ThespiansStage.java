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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class ThespiansStage extends CardImpl<ThespiansStage> {

    public ThespiansStage(UUID ownerId) {
        super(ownerId, 248, "Thespian's Stage", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "GTC";

        // {T}: Add 1 to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // 2, {T}: Thespian's Stage becomes a copy of target land and gains this ability.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ThespiansStageCopyEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

    }

    public ThespiansStage(final ThespiansStage card) {
        super(card);
    }

    @Override
    public ThespiansStage copy() {
        return new ThespiansStage(this);
    }
}

class ThespiansStageCopyEffect extends OneShotEffect<ThespiansStageCopyEffect> {

    public ThespiansStageCopyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Thespian's Stage becomes a copy of target land and gains this ability";
    }

    public ThespiansStageCopyEffect(final ThespiansStageCopyEffect effect) {
        super(effect);
    }

    @Override
    public ThespiansStageCopyEffect copy() {
        return new ThespiansStageCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            Permanent newPermanent = game.copyPermanent(copyFromPermanent, sourcePermanent, source, new EmptyApplyToPermanent());
            Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ThespiansStageCopyEffect(), new GenericManaCost(2));
            ability.addCost(new TapSourceCost());
            ability.addTarget(new TargetLandPermanent());
            newPermanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
