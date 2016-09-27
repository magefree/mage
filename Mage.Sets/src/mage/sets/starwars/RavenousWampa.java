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
package mage.sets.starwars;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class RavenousWampa extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public RavenousWampa(UUID ownerId) {
        super(ownerId, 229, "Ravenous Wampa", Rarity.NA/*UNCOMMON*/, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}");
        this.expansionSetCode = "SWS";
        this.subtype.add("Beast");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}{G}, Sacrifice another creature: Monstrosity 2.
        Ability ability = new MonstrosityAbility("{1}{G}", 2);
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);

        // When Ravenous Wampa becomes monstrous, you gain life equal to the sacrificied creature's toughness.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new RavenousWampaEffect()));
    }

    public RavenousWampa(final RavenousWampa card) {
        super(card);
    }

    @Override
    public RavenousWampa copy() {
        return new RavenousWampa(this);
    }
}

class RavenousWampaEffect extends OneShotEffect {

    public RavenousWampaEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain life equal to the sacrificied creature's toughness";
    }

    public RavenousWampaEffect(final RavenousWampaEffect effect) {
        super(effect);
    }

    @Override
    public RavenousWampaEffect copy() {
        return new RavenousWampaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // NEED TO GET SACKED PERMANENT
        Permanent p = null;
        if (p != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(p.getToughness().getValue(), game);
                return true;
            }

        }
        return false;

    }
}
