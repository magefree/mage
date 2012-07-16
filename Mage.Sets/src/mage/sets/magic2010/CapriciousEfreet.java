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
package mage.sets.magic2010;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class CapriciousEfreet extends CardImpl<CapriciousEfreet> {

    private static final FilterNonlandPermanent filterControlled = new FilterNonlandPermanent("nonland permanent you control");
    private static final FilterNonlandPermanent filterNotControlled = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filterControlled.add(new ControllerPredicate(TargetController.YOU));
        filterNotControlled.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public CapriciousEfreet(UUID ownerId) {
        super(ownerId, 131, "Capricious Efreet", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "M10";
        this.subtype.add("Efreet");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, choose target nonland permanent you control and up to two target nonland permanents you don't control. Destroy one of them at random.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CapriciousEfreetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(filterControlled));
        ability.addTarget(new TargetPermanent(0, 2, filterNotControlled, false));
        this.addAbility(ability);
    }

    public CapriciousEfreet(final CapriciousEfreet card) {
        super(card);
    }

    @Override
    public CapriciousEfreet copy() {
        return new CapriciousEfreet(this);
    }
}

class CapriciousEfreetEffect extends OneShotEffect<CapriciousEfreetEffect> {

    public CapriciousEfreetEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "choose target nonland permanent you control and up to two target nonland permanents you don't control. Destroy one of them at random";
    }

    public CapriciousEfreetEffect(final CapriciousEfreetEffect effect) {
        super(effect);
    }

    @Override
    public CapriciousEfreetEffect copy() {
        return new CapriciousEfreetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ArrayList<Permanent> targetPermanents = new ArrayList<Permanent>();
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            targetPermanents.add(permanent);
        }
        for (UUID targetID : source.getTargets().get(1).getTargets()) {
            permanent = game.getPermanent(targetID);
            if (permanent != null) {
                targetPermanents.add(permanent);
            }
        }

        if (!targetPermanents.isEmpty()) {
            Random random = new Random();
            permanent = targetPermanents.get(random.nextInt(targetPermanents.size()));
            permanent.destroy(source.getId(), game, false);
            return true;
        }
        return false;
    }
}
