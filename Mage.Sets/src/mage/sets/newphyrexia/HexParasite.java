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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public class HexParasite extends CardImpl<HexParasite> {

    public HexParasite(UUID ownerId) {
        super(ownerId, 137, "Hex Parasite", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Insect");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{BP}: Remove up to X counters from target permanent. For each counter removed this way, Hex Parasite gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HexParasiteEffect(), new ManaCostsImpl("{X}{BP}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    public HexParasite(final HexParasite card) {
        super(card);
    }

    @Override
    public HexParasite copy() {
        return new HexParasite(this);
    }
}

class HexParasiteEffect extends OneShotEffect<HexParasiteEffect> {

    HexParasiteEffect ( ) {
        super(Outcome.Benefit);
        staticText = "Remove up to X counters from target permanent. For each counter removed this way, {this} gets +1/+0 until end of turn";
    }

    HexParasiteEffect ( HexParasiteEffect effect ) {
        super(effect);
    }

    @Override
    public HexParasiteEffect copy() {
        return new HexParasiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetPermanent target = (TargetPermanent)source.getTargets().get(0);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            int toRemove = source.getManaCostsToPay().getX();
            int removed = 0;
            String[] counterNames = permanent.getCounters().keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (player.chooseUse(Outcome.Neutral, "Do you want to remove " + counterName + " counters?", game)) {
                    if (permanent.getCounters().get(counterName).getCount() == 1 || toRemove == 1) {
                        permanent.getCounters().removeCounter(counterName, 1);
                    }
                    else {
                        int amount = player.getAmount(1, Math.min(permanent.getCounters().get(counterName).getCount(), toRemove - removed), "How many?", game);
                        if (amount > 0) {
                            removed += amount;
                            permanent.getCounters().removeCounter(counterName, amount);
                        }
                    }
                }
                if (removed >= toRemove)
                    break;
            }
            game.addEffect(new BoostSourceEffect(removed, 0, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

}
