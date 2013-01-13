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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.sets.tokens.EmptyToken;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class GiantAdephage extends CardImpl<GiantAdephage> {

    public GiantAdephage(UUID ownerId) {
        super(ownerId, 121, "Giant Adephage", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Insect");
        this.color.setGreen(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Giant Adephage deals combat damage to a player, put a token onto the battlefield that is a copy of Giant Adephage.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GiantAdephageCopyEffect(), false));

    }

    public GiantAdephage(final GiantAdephage card) {
        super(card);
    }

    @Override
    public GiantAdephage copy() {
        return new GiantAdephage(this);
    }
}

class GiantAdephageCopyEffect extends OneShotEffect<GiantAdephageCopyEffect> {

    public GiantAdephageCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "put a token onto the battlefield that is a copy of Giant Adephage";
    }

    public GiantAdephageCopyEffect(final GiantAdephageCopyEffect effect) {
        super(effect);
    }

    @Override
    public GiantAdephageCopyEffect copy() {
        return new GiantAdephageCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject thisCard = game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
        if (thisCard != null && thisCard instanceof Permanent) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from((Permanent)thisCard);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        } else { // maybe it's token
            Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
            if (permanent != null) {
                EmptyToken token = new EmptyToken();
                CardUtil.copyTo(token).from(permanent);
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
