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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author TheElk801
 */
public class MirrorOfTheForebears extends CardImpl {

    public MirrorOfTheForebears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Mirror of the Forebears enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Copy)));

        // 1: Until end of turn, Mirror of the Forebears becomes a copy of target creature you control of the chosen type, except it's an artifact in addition to its other types.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        filter.add(new ChosenSubtypePredicate(this.getId()));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MirrorOfTheForebearsCopyEffect(), new ManaCostsImpl("{1}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public MirrorOfTheForebears(final MirrorOfTheForebears card) {
        super(card);
    }

    @Override
    public MirrorOfTheForebears copy() {
        return new MirrorOfTheForebears(this);
    }
}

class MirrorOfTheForebearsCopyEffect extends OneShotEffect {

    public MirrorOfTheForebearsCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "until end of turn, {this} becomes a copy of target creature you control of the chosen type, except it's an artifact in addition to its other types";
    }

    public MirrorOfTheForebearsCopyEffect(final MirrorOfTheForebearsCopyEffect effect) {
        super(effect);
    }

    @Override
    public MirrorOfTheForebearsCopyEffect copy() {
        return new MirrorOfTheForebearsCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent.getId(), source, new EmptyApplyToPermanent());
            if (!copyFromPermanent.isArtifact()) {
                ContinuousEffect effect = new AddCardTypeTargetEffect(CardType.ARTIFACT, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
