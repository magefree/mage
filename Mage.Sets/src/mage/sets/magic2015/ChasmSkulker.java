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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ChasmSkulker extends CardImpl {

    public ChasmSkulker(UUID ownerId) {
        super(ownerId, 46, "Chasm Skulker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "M15";
        this.subtype.add("Squid");
        this.subtype.add("Horror");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
        
        // When Chasm Skulker dies, put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker.
        this.addAbility(new DiesTriggeredAbility(new ChasmSkulkerEffect(), false));
    }

    public ChasmSkulker(final ChasmSkulker card) {
        super(card);
    }

    @Override
    public ChasmSkulker copy() {
        return new ChasmSkulker(this);
    }
}

class ChasmSkulkerEffect extends OneShotEffect {

    public ChasmSkulkerEffect() {
        super(Outcome.Benefit);
        this.staticText = "put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker";
    }

    public ChasmSkulkerEffect(final ChasmSkulkerEffect effect) {
        super(effect);
    }

    @Override
    public ChasmSkulkerEffect copy() {
        return new ChasmSkulkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent != null) {
                int counters = permanent.getCounters().getCount(CounterType.P1P1);
                if (counters > 0) {
                    return new CreateTokenEffect(new ChasmSkulkerSquidToken(), counters).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}

class ChasmSkulkerSquidToken extends Token {

    public ChasmSkulkerSquidToken() {
        super("Squid", "1/1 blue Squid creature token with islandwalk");
        this.setOriginalExpansionSetCode("M15");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Squid");
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new IslandwalkAbility());
    }

}