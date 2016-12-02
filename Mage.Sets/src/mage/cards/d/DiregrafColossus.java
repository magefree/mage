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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class DiregrafColossus extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Zombie spell");

    static {
        filter.add(new SubtypePredicate("Zombie"));
    }

    public DiregrafColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add("Zombie");
        this.subtype.add("Giant");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Diregraf Colossus enters the battlefield with a +1/+1 counter on it for each Zombie card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new DiregrafColossusEffect(), "with a +1/+1 counter on it for each Zombie card in your graveyard"));

        // Whenever you cast a Zombie spell, create a tapped 2/2 black Zombie creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 1, true, false), filter, false));

    }

    public DiregrafColossus(final DiregrafColossus card) {
        super(card);
    }

    @Override
    public DiregrafColossus copy() {
        return new DiregrafColossus(this);
    }
}

class DiregrafColossusEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(new SubtypePredicate("Zombie"));
    }

    public DiregrafColossusEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each Zombie card in your graveyard";
    }

    public DiregrafColossusEffect(final DiregrafColossusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = 0;
            amount += player.getGraveyard().count(filter, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DiregrafColossusEffect copy() {
        return new DiregrafColossusEffect(this);
    }

}
