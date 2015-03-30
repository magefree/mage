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
package mage.sets.magicplayerrewards;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class PowderKeg extends CardImpl {

    public PowderKeg(UUID ownerId) {
        super(ownerId, 3, "Powder Keg", Rarity.SPECIAL, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MPRP";

        // At the beginning of your upkeep, you may put a fuse counter on Powder Keg.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.FUSE.createInstance(), true), TargetController.YOU, true));
        
        // {T}, Sacrifice Powder Keg: Destroy each artifact and creature with converted mana cost equal to the number of fuse counters on Powder Keg.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PowderKegEffect(), new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public PowderKeg(final PowderKeg card) {
        super(card);
    }

    @Override
    public PowderKeg copy() {
        return new PowderKeg(this);
    }
}

class PowderKegEffect extends OneShotEffect {

        public PowderKegEffect() {
            super(Outcome.DestroyPermanent);
            staticText = "Destroy each artifact and creature with converted mana cost equal to the number of fuse counters on Powder Keg {this}";
        }

        public PowderKegEffect(final PowderKegEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent p = game.getBattlefield().getPermanent(source.getSourceId());
            if (p == null) {
                p = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
                if (p == null) {
                    return false;
                }
            }

            int count = p.getCounters().getCount(CounterType.FUSE);
            for (Permanent perm: game.getBattlefield().getAllActivePermanents()) {
                if (perm.getManaCost().convertedManaCost() == count && ((perm.getCardType().contains(CardType.ARTIFACT)) 
                        || (perm.getCardType().contains(CardType.CREATURE)))) {
                    perm.destroy(source.getSourceId(), game, false);
                }
            }

            return true;
        }

        @Override
        public PowderKegEffect copy() {
            return new PowderKegEffect(this);
        }

    }