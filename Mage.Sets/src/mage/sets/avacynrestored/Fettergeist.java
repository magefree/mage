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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class Fettergeist extends CardImpl<Fettergeist> {

    public Fettergeist(UUID ownerId) {
        super(ownerId, 52, "Fettergeist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, sacrifice Fettergeist unless you pay {1} for each other creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FettergeistUnlessPaysEffect(), Constants.TargetController.YOU, false));

    }

    public Fettergeist(final Fettergeist card) {
        super(card);
    }

    @Override
    public Fettergeist copy() {
        return new Fettergeist(this);
    }
}

class FettergeistUnlessPaysEffect extends OneShotEffect<FettergeistUnlessPaysEffect> {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.setAnother(true);
    }

    public FettergeistUnlessPaysEffect() {
        super(Constants.Outcome.Sacrifice);
        staticText = "sacrifice {this} unless you pay {1} for each other creature you control.";
    }

    public FettergeistUnlessPaysEffect(final FettergeistUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter, 1);
            int count = amount.calculate(game, source);
            if (count == 0) {
                return true;
            }
            if (player.chooseUse(Constants.Outcome.Benefit, "Pay " + count + "?", game)) {
                GenericManaCost cost = new GenericManaCost(count);
                if (cost.pay(source, game, source.getId(), source.getControllerId(), false)) {
                    return true;
                }
            }
            permanent.sacrifice(source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public FettergeistUnlessPaysEffect copy() {
        return new FettergeistUnlessPaysEffect(this);
    }

}

