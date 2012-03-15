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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.CantBeBlockedByOneEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author intimidatingant
 */
public class PyreheartWolf extends CardImpl<PyreheartWolf> {

    public PyreheartWolf(UUID ownerId) {
        super(ownerId, 101, "Pyreheart Wolf", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Wolf");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Pyreheart Wolf attacks, each creature you control can't be blocked this turn except by two or more creatures.
        this.addAbility(new UndyingAbility());
        this.addAbility(new AttacksTriggeredAbility(new PyreheartWolfEffect(), false));
    }

    public PyreheartWolf(final PyreheartWolf card) {
        super(card);
    }

    @Override
    public PyreheartWolf copy() {
        return new PyreheartWolf(this);
    }
}

class PyreheartWolfEffect extends OneShotEffect<PyreheartWolfEffect> {

    public PyreheartWolfEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "creatures you control can't be blocked except by two or more creatures until end of turn";
    }

    public PyreheartWolfEffect(final PyreheartWolfEffect effect) {
        super(effect);
    }

    @Override
    public PyreheartWolfEffect copy() {
        return new PyreheartWolfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId())) {
            CantBeBlockedByOneEffect effect = new CantBeBlockedByOneEffect(2, Duration.EndOfTurn);
            SimpleStaticAbility ability = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect);
            perm.addAbility(ability, game);
        }
        return false;
    }
}
