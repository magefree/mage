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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author anonymous
 */
public class ArchdemonOfGreed extends CardImpl<ArchdemonOfGreed> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Human");

    static {
        filter.add(new SubtypePredicate("Human"));
        filter.setTargetController(Constants.TargetController.YOU);
    }

    public ArchdemonOfGreed(UUID ownerId) {
        super(ownerId, 71, "Archdemon of Greed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "DKA";
        this.subtype.add("Demon");

        this.nightCard = true;
        this.canTransform = true;

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new ArchdemonOfGreedEffect(), false));
    }

    public ArchdemonOfGreed(final ArchdemonOfGreed card) {
        super(card);
    }

    @Override
    public ArchdemonOfGreed copy() {
        return new ArchdemonOfGreed(this);
    }

    class ArchdemonOfGreedEffect extends OneShotEffect<ArchdemonOfGreedEffect> {

        public ArchdemonOfGreedEffect() {
            super(Constants.Outcome.Damage);
            this.staticText = "Sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you.";
        }

        public ArchdemonOfGreedEffect(final ArchdemonOfGreedEffect effect) {
            super(effect);
        }

        @Override
        public ArchdemonOfGreedEffect copy() {
            return new ArchdemonOfGreedEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());

            if (permanent != null) {
                // create cost for sacrificing a human
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);
                    // if they can pay the cost, then they must pay
                    if (target.canChoose(player.getId(), game)) {
                        while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                            player.choose(Constants.Outcome.Sacrifice, target, source.getSourceId(), game);
                        }
                        Permanent humanSacrifice = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            // sacrifice the chosen card
                            return humanSacrifice.sacrifice(source.getId(), game);
                        }
                    }         
                    else {
                        permanent.tap(game);
                        player.damage(9, source.getId(), game, false, true);
                    }
                }
            }
            return true;
        }
    }
}
