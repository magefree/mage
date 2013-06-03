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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class NecromancersCovenant extends CardImpl<NecromancersCovenant> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Zombies you control");
    
    static {
        filter.add(new SubtypePredicate("Zombie"));
    }

    public NecromancersCovenant(UUID ownerId) {
        super(ownerId, 82, "Necromancer's Covenant", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{B}{B}");
        this.expansionSetCode = "ARB";

        this.color.setBlack(true);
        this.color.setWhite(true);

        // When Necromancer's Covenant enters the battlefield, exile all creature cards from target player's graveyard, then put a 2/2 black Zombie creature token onto the battlefield for each card exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NecromancersConvenantEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Zombies you control have lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    public NecromancersCovenant(final NecromancersCovenant card) {
        super(card);
    }

    @Override
    public NecromancersCovenant copy() {
        return new NecromancersCovenant(this);
    }
}

class NecromancersConvenantEffect extends OneShotEffect<NecromancersConvenantEffect> {

    public NecromancersConvenantEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile all creature cards from target player's graveyard, then put a 2/2 black Zombie creature token onto the battlefield for each card exiled this way";
    }

    public NecromancersConvenantEffect(NecromancersConvenantEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        int count = 0;
        for (Card card : player.getGraveyard().getCards(new FilterCreatureCard(), game)) {
            if (card.moveToExile(source.getSourceId(), "Necromancer Covenant", source.getSourceId(), game)) {
                count += 1;
            }
        }
        ZombieToken zombieToken = new ZombieToken();
        if (zombieToken.putOntoBattlefield(count, game, source.getId(), source.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public NecromancersConvenantEffect copy() {
        return new NecromancersConvenantEffect(this);
    }
}
