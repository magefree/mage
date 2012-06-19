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
package mage.sets.innistrad;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardEachPlayerEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class LilianaOfTheVeil extends CardImpl<LilianaOfTheVeil> {

    public LilianaOfTheVeil(UUID ownerId) {
        super(ownerId, 105, "Liliana of the Veil", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Liliana");

        this.color.setBlack(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), ""));

        // +1: Each player discards a card.
        this.addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(), 1));
        // -2: Target player sacrifices a creature.
        LoyaltyAbility ability = new LoyaltyAbility(new SacrificeEffect(new FilterCreaturePermanent(), 1, "Target player"), -2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // -6: Separate all permanents target player controls into two piles. That player sacrifices all permanents in the pile of his or her choice.
        ability = new LoyaltyAbility(new LilianaOfTheVeilEffect(), -6);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public LilianaOfTheVeil(final LilianaOfTheVeil card) {
        super(card);
    }

    @Override
    public LilianaOfTheVeil copy() {
        return new LilianaOfTheVeil(this);
    }
}

class LilianaOfTheVeilEffect extends OneShotEffect<LilianaOfTheVeilEffect> {

    public LilianaOfTheVeilEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Separate all permanents target player controls into two piles. That player sacrifices all permanents in the pile of his or her choice";
    }

    public LilianaOfTheVeilEffect(final LilianaOfTheVeilEffect effect) {
        super(effect);
    }

    @Override
    public LilianaOfTheVeilEffect copy() {
        return new LilianaOfTheVeilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            int count = game.getBattlefield().countAll(new FilterPermanent(), targetPlayer.getId(), game);
            TargetPermanent target = new TargetPermanent(0, count, new FilterPermanent("permanents to put in the first pile"), false);
            List<Permanent> pile1 = new ArrayList<Permanent>();
            if (player.choose(Outcome.Neutral, target, source.getSourceId(), game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent p = game.getPermanent(targetId);
                    if (p != null) {
                        pile1.add(p);
                    }
                }
            }
            List<Permanent> pile2 = new ArrayList<Permanent>();
            for (Permanent p: game.getBattlefield().getAllActivePermanents(targetPlayer.getId())) {
                if (!pile1.contains(p)) {
                    pile2.add(p);
                }
            }

            boolean choice = targetPlayer.choosePile(Outcome.DestroyPermanent, "Choose a pile to sacrifice.", pile1, pile2, game);

            if (choice) {
                sacrificePermanents(pile1, game, source);
            } else {
                sacrificePermanents(pile2, game, source);
            }

            return true;
        }
        return false;
    }

    private void sacrificePermanents(List<Permanent> pile, Game game, Ability source) {
        for (Permanent permanent : pile) {
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
    }
}
