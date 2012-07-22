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
package mage.sets.worldwake;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ButcherOfMalakir extends CardImpl<ButcherOfMalakir> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    public ButcherOfMalakir(UUID ownerId) {
        super(ownerId, 53, "Butcher of Malakir", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Butcher of Malakir or another creature you control dies, each opponent sacrifices a creature.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new ButcherOfMalakirEffect(), false, filter));
    }

    public ButcherOfMalakir(final ButcherOfMalakir card) {
        super(card);
    }

    @Override
    public ButcherOfMalakir copy() {
        return new ButcherOfMalakir(this);
    }
}
class ButcherOfMalakirEffect extends OneShotEffect<ButcherOfMalakirEffect> {

    public ButcherOfMalakirEffect() {
        super(Constants.Outcome.Sacrifice);
        this.staticText = "Each opponent sacrifices a creature";
    }

    public ButcherOfMalakirEffect(final ButcherOfMalakirEffect effect) {
        super(effect);
    }

    @Override
    public ButcherOfMalakirEffect copy() {
        return new ButcherOfMalakirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledPermanent filter = new FilterControlledPermanent("creature you control");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentId : opponents) {
            Player player = game.getPlayer(opponentId);
            Target target = new TargetControlledPermanent(filter);

            if (target.canChoose(player.getId(), game)) {
                while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                    player.choose(Constants.Outcome.Sacrifice, target, source.getSourceId(), game);
                }

                Permanent permanent = game.getPermanent(target.getFirstTarget());

                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return true;
    }
}
