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
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import static mage.cards.u.UnstableShapeshifter.filterAnotherCreature;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author HCrescent
 */
public class UnstableShapeshifter extends CardImpl {

    final static FilterCreaturePermanent filterAnotherCreature = new FilterCreaturePermanent("another creature");

    static {
        filterAnotherCreature.add(new AnotherPredicate());
    }

    public UnstableShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield, Unstable Shapeshifter becomes a copy of that creature and gains this ability.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new UnstableShapeshifterEffect(), filterAnotherCreature, false, SetTargetPointer.PERMANENT, ""));
    }

    public UnstableShapeshifter(final UnstableShapeshifter card) {
        super(card);
    }

    @Override
    public UnstableShapeshifter copy() {
        return new UnstableShapeshifter(this);
    }
}

class UnstableShapeshifterEffect extends OneShotEffect {

    public UnstableShapeshifterEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of that creature and gains this ability";
    }

    public UnstableShapeshifterEffect(final UnstableShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public UnstableShapeshifterEffect copy() {
        return new UnstableShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent targetCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (targetCreature != null && permanent != null) {
            Permanent blueprintPermanent = game.copyPermanent(Duration.Custom, targetCreature, permanent.getId(), source, new EmptyApplyToPermanent());
            blueprintPermanent.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                    new UnstableShapeshifterEffect(), filterAnotherCreature, false, SetTargetPointer.PERMANENT, ""), game);
            return true;
        }
        return false;
    }
}
