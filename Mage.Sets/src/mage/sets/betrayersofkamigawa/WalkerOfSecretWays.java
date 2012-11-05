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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.OnlyDuringYourTurnCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WalkerOfSecretWays extends CardImpl<WalkerOfSecretWays> {

    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("Ninja you control");
    static {
        filterCreature.add((new SubtypePredicate("Ninja")));
    }

    public WalkerOfSecretWays(UUID ownerId) {
        super(ownerId, 60, "Walker of Secret Ways", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Human");
        this.subtype.add("Ninja");
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{U} ({1}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{1}{U}")));

        // Whenever Walker of Secret Ways deals combat damage to a player, look at that player's hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new WalkerOfSecretWaysEffect(), true, true));

        // {1}{U}: Return target Ninja you control to its owner's hand. Activate this ability only during your turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{1}{U}"));
        ability.addTarget(new TargetControlledCreaturePermanent(1,1, filterCreature, false));
        ability.addCost(new OnlyDuringYourTurnCost());
        this.addAbility(ability);


    }

    public WalkerOfSecretWays(final WalkerOfSecretWays card) {
        super(card);
    }

    @Override
    public WalkerOfSecretWays copy() {
        return new WalkerOfSecretWays(this);
    }
}

class WalkerOfSecretWaysEffect extends OneShotEffect<WalkerOfSecretWaysEffect> {
    WalkerOfSecretWaysEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "look at that player's hand";
    }

    WalkerOfSecretWaysEffect(final WalkerOfSecretWaysEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && controller != null) {
            controller.lookAtCards("Walker of Secret Ways", player.getHand(), game);
        }
        return true;
    }

    @Override
    public WalkerOfSecretWaysEffect copy() {
        return new WalkerOfSecretWaysEffect(this);
    }

}