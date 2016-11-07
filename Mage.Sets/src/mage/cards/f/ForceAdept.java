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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class ForceAdept extends CardImpl {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("another target creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature you don't control");

    static {
        filter1.add(new AnotherPredicate());
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public ForceAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{W}");
        this.subtype.add("Mirialan");
        this.subtype.add("Jedi");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Force Adept enters the battlefield, return another target creature you control and target creature you don't control to their owner's hands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ForceAdeptEffect());
        ability.addTarget(new TargetControlledCreaturePermanent(filter1));
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    public ForceAdept(final ForceAdept card) {
        super(card);
    }

    @Override
    public ForceAdept copy() {
        return new ForceAdept(this);
    }
}

class ForceAdeptEffect extends OneShotEffect {

    public ForceAdeptEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return another target creature you control and target creature you don't control to their owners' hands";
    }

    public ForceAdeptEffect(final ForceAdeptEffect effect) {
        super(effect);
    }

    @Override
    public ForceAdeptEffect copy() {
        return new ForceAdeptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }

        return result;
    }
}
