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
package mage.sets.conspiracytakethecrown;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author maxlebedev
 */
public class SelvalaHeartOfTheWilds extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new GreatestPowerPredicate());
    }

    private static final String rule = "Whenever another creature enters the battlefield, its controller may draw a card if its power is greater than each other creature's power";

    public SelvalaHeartOfTheWilds(UUID ownerId) {
        super(ownerId, 70, "Selvala, Heart of the Wilds", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "CN2";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Scout");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield, its controller may draw a card if its power is greater than each other creature's power.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SelvalaHeartOfTheWildsEffect(), filter, false, SetTargetPointer.PERMANENT, rule));

        // {G}, {T}: Add X mana in any combination of colors to your mana pool, where X is the greatest power among creatures you control.
        this.addAbility(new DynamicManaAbility(new Mana(0, 0, 0, 0, 0, 0, 1, 0), new GreatestPowerYouControlValue(), new TapSourceCost(),
                "Add X mana in any combination of colors to your mana pool, where X is the greatest power among creatures you control."));
    }

    public SelvalaHeartOfTheWilds(final SelvalaHeartOfTheWilds card) {
        super(card);
    }

    @Override
    public SelvalaHeartOfTheWilds copy() {
        return new SelvalaHeartOfTheWilds(this);
    }
}

class SelvalaHeartOfTheWildsEffect extends OneShotEffect {

    public SelvalaHeartOfTheWildsEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller may draw a card";
    }

    public SelvalaHeartOfTheWildsEffect(final SelvalaHeartOfTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public SelvalaHeartOfTheWildsEffect copy() {
        return new SelvalaHeartOfTheWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            Player cardowner = game.getPlayer(permanent.getControllerId());
            if (cardowner.chooseUse(Outcome.DrawCard, "Would you like to draw a card?", source, game)) {
                cardowner.drawCards(1, game);
            }
        }
        return true;
    }
}

class GreatestPowerPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        int pow = input.getObject().getPower().getValue();

        for (UUID id : game.getPlayerList()) {
            Player player = game.getPlayer(id);
            if (player != null) {
                for (Permanent p : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), id, game)) {
                    if (p.getPower().getValue() >= pow && !p.equals(input.getObject())) {
                        return false; //we found something with equal/more power
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Greatest Power";
    }
}

class GreatestPowerYouControlValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        int amount = 0;
        if (player != null) {
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)) {
                if (p.getPower().getValue() > amount) {
                    amount = p.getPower().getValue();
                }
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new GreatestPowerYouControlValue();
    }

    @Override
    public String getMessage() {
        return "Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.";
    }
}
