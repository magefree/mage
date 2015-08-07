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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Quickling extends CardImpl {

    public Quickling(UUID ownerId) {
        super(ownerId, 76, "Quickling", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "M15";
        this.subtype.add("Faerie");
        this.subtype.add("Rogue");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Quickling enters the battlefield, sacrifice it unless you return another creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new QuicklingEffect()));
    }

    public Quickling(final Quickling card) {
        super(card);
    }

    @Override
    public Quickling copy() {
        return new Quickling(this);
    }
}

class QuicklingEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");
    private static final String effectText = "sacrifice it unless you return another creature you control to its owner's hand";

    static {
        filter.add(new AnotherPredicate());
    }

    QuicklingEffect() {
        super(Outcome.ReturnToHand);
        staticText = effectText;
    }

    QuicklingEffect(QuicklingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean targetChosen = false;
            TargetPermanent target = new TargetPermanent(1, 1, filter, true);
            if (target.canChoose(controller.getId(), game) && controller.chooseUse(outcome, "Return another creature you control to its owner's hand?", source, game)) {
                controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    targetChosen = true;
                    controller.moveCards(permanent, null, Zone.HAND, source, game);
                }
            }

            if (!targetChosen) {
                new SacrificeSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public QuicklingEffect copy() {
        return new QuicklingEffect(this);
    }

}
