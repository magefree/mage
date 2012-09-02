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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jonubuu
 */
public class GolgariGraveTroll extends CardImpl<GolgariGraveTroll> {

    public GolgariGraveTroll(UUID ownerId) {
        super(ownerId, 167, "Golgari Grave-Troll", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Skeleton");
        this.subtype.add("Troll");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Golgari Grave-Troll enters the battlefield with a +1/+1 counter on it for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new GolgariGraveTrollEffect(), "with a +1/+1 counter on it for each creature card in your graveyard"));
        // {1}, Remove a +1/+1 counter from Golgari Grave-Troll: Regenerate Golgari Grave-Troll.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
        // Dredge 6
        this.addAbility(new DredgeAbility(6));
    }

    public GolgariGraveTroll(final GolgariGraveTroll card) {
        super(card);
    }

    @Override
    public GolgariGraveTroll copy() {
        return new GolgariGraveTroll(this);
    }
}

class GolgariGraveTrollEffect extends OneShotEffect<GolgariGraveTrollEffect> {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {

        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public GolgariGraveTrollEffect() {
        super(Outcome.BoostCreature);
    }

    public GolgariGraveTrollEffect(final GolgariGraveTrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = player.getGraveyard().count(filter, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GolgariGraveTrollEffect copy() {
        return new GolgariGraveTrollEffect(this);
    }
}
