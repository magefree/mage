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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Cytoshape extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public Cytoshape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{U}");

        // Choose a nonlegendary creature on the battlefield. Target creature becomes a copy of that creature until end of turn.
        this.getSpellAbility().addEffect(new CytoshapeEffect());
        Target target = new TargetCreaturePermanent(1, 1, filter, true);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("target creature that will become a copy of that chosen creature");
        target = new TargetCreaturePermanent(filter2);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

    }

    public Cytoshape(final Cytoshape card) {
        super(card);
    }

    @Override
    public Cytoshape copy() {
        return new Cytoshape(this);
    }
}

class CytoshapeEffect extends OneShotEffect {

    public CytoshapeEffect() {
        super(Outcome.Copy);
        this.staticText = "Choose a nonlegendary creature on the battlefield. Target creature becomes a copy of that creature until end of turn.";
    }

    public CytoshapeEffect(final CytoshapeEffect effect) {
        super(effect);
    }

    @Override
    public CytoshapeEffect copy() {
        return new CytoshapeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Permanent copyFrom = game.getPermanent(getTargetPointer().getFirst(game, ability));
        if (copyFrom != null) {
            Permanent copyTo = game.getPermanentOrLKIBattlefield(ability.getTargets().get(1).getFirstTarget());
            if (copyTo != null) {
                game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), ability, new EmptyApplyToPermanent());
            }
        }
        return true;
    }
}
