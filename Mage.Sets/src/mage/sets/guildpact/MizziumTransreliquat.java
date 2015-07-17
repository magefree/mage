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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author fireshoes
 */
public class MizziumTransreliquat extends CardImpl {

    public MizziumTransreliquat(UUID ownerId) {
        super(ownerId, 153, "Mizzium Transreliquat", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "GPT";

        // {3}: Mizzium Transreliquat becomes a copy of target artifact until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MizziumTransreliquatCopyEffect(), new ManaCostsImpl("{3}"));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
        
        // {1}{U}{R}: Mizzium Transreliquat becomes a copy of target artifact and gains this ability.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MizziumTransreliquatCopyAndGainAbilityEffect(), new ManaCostsImpl("{1}{U}{R}"));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    public MizziumTransreliquat(final MizziumTransreliquat card) {
        super(card);
    }

    @Override
    public MizziumTransreliquat copy() {
        return new MizziumTransreliquat(this);
    }
}


class MizziumTransreliquatCopyEffect extends OneShotEffect {

    public MizziumTransreliquatCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "Mizzium Transreliquat becomes a copy of target artifact until end of turn";
    }

    public MizziumTransreliquatCopyEffect(final MizziumTransreliquatCopyEffect effect) {
        super(effect);
    }

    @Override
    public MizziumTransreliquatCopyEffect copy() {
        return new MizziumTransreliquatCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent, source, new EmptyApplyToPermanent());
            return true;
        }
        return false;
    }
}
class MizziumTransreliquatCopyAndGainAbilityEffect extends OneShotEffect {

    public MizziumTransreliquatCopyAndGainAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Mizzium Transreliquat becomes a copy of target artifact and gains this ability";
    }

    public MizziumTransreliquatCopyAndGainAbilityEffect(final MizziumTransreliquatCopyAndGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public MizziumTransreliquatCopyAndGainAbilityEffect copy() {
        return new MizziumTransreliquatCopyAndGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            Permanent newPermanent = game.copyPermanent(copyFromPermanent, sourcePermanent, source, new EmptyApplyToPermanent());
            Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MizziumTransreliquatCopyAndGainAbilityEffect(), new ManaCostsImpl("{1}{U}{R}"));
            ability.addTarget(new TargetArtifactPermanent());
            newPermanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}