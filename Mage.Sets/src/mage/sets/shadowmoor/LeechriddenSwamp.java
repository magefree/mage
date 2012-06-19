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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.cards.CardImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.Mana;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.abilities.effects.OneShotEffect;
import mage.players.Player;
import mage.players.Players;
import mage.Constants.Outcome;

/**
 *
 * @author jeffwadsworth
 */
public class LeechriddenSwamp extends CardImpl<LeechriddenSwamp> {

    public LeechriddenSwamp(UUID ownerId) {
        super(ownerId, 273, "Leechridden Swamp", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SHM";
        this.subtype.add("Swamp");

        // <i>({tap}: Add {B} to your mana pool.)</i>
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana), new TapSourceCost()));

        // Leechridden Swamp enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {B}, {tap}: Each opponent loses 1 life. Activate this ability only if you control two or more black permanents.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new LeechriddenSwampLoseLifeEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new ControlTwoOrMoreBlackPermanentsCost());
        this.addAbility(ability);
    }

    public LeechriddenSwamp(final LeechriddenSwamp card) {
        super(card);
    }

    @Override
    public LeechriddenSwamp copy() {
        return new LeechriddenSwamp(this);
    }
}

class ControlTwoOrMoreBlackPermanentsCost extends CostImpl<ControlTwoOrMoreBlackPermanentsCost> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.getColor().setBlack(true);
        filter.setUseColor(true);
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public ControlTwoOrMoreBlackPermanentsCost() {
        this.text = "Activate this ability only if you control two or more black permanents";
    }

    public ControlTwoOrMoreBlackPermanentsCost(final ControlTwoOrMoreBlackPermanentsCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, controllerId, 2, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
            return paid;
    }

    @Override
    public ControlTwoOrMoreBlackPermanentsCost copy() {
        return new ControlTwoOrMoreBlackPermanentsCost(this);
    }
}

class LeechriddenSwampLoseLifeEffect extends OneShotEffect<LeechriddenSwampLoseLifeEffect> {

    private static final String effectText = "each opponent loses 1 life";

    LeechriddenSwampLoseLifeEffect ( ) {
        super(Outcome.Damage);
    staticText = effectText;
    }

    LeechriddenSwampLoseLifeEffect ( LeechriddenSwampLoseLifeEffect effect ) {
    super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
    Players players = game.getPlayers();
        for ( Player player : players.values() ) {
            if ( !player.getId().equals(source.getControllerId()) ) {
                player.loseLife(1, game);
            }
    }
    return true;
    }

    @Override
    public LeechriddenSwampLoseLifeEffect copy() {
    return new LeechriddenSwampLoseLifeEffect(this);
    }
}
