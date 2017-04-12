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
package mage.cards.l;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.players.Players;

/**
 *
 * @author jeffwadsworth
 */
public class LeechriddenSwamp extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control two or more black permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public LeechriddenSwamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add("Swamp");

        // ({tap}: Add {B} to your mana pool.)
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost()));

        // Leechridden Swamp enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {B}, {tap}: Each opponent loses 1 life. Activate this ability only if you control two or more black permanents.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new LeechriddenSwampLoseLifeEffect(),
                new ManaCostsImpl("{B}"),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1));
        ability.addCost(new TapSourceCost());
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

class LeechriddenSwampLoseLifeEffect extends OneShotEffect {

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
                player.loseLife(1, game, false);
            }
    }
    return true;
    }

    @Override
    public LeechriddenSwampLoseLifeEffect copy() {
    return new LeechriddenSwampLoseLifeEffect(this);
    }
}
