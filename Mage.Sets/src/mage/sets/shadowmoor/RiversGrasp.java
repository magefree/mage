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
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class RiversGrasp extends CardImpl {

    public RiversGrasp(UUID ownerId) {
        super(ownerId, 174, "River's Grasp", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{U/B}");
        this.expansionSetCode = "SHM";

        // If {U} was spent to cast River's Grasp, return up to one target creature to its owner's hand. If {B} was spent to cast River's Grasp, target player reveals his or her hand, you choose a nonland card from it, then that player discards that card.
        Target targetCreature = new TargetCreaturePermanent(0, 1);
        Target targetPlayer = new TargetPlayer();
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnToHandTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.U), "If {U} was spent to cast {this}, return up to one target creature to its owner's hand"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RiversGraspEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.B), " If {B} was spent to cast {this}, target player reveals his or her hand, you choose a nonland card from it, then that player discards that card"));

        this.getSpellAbility().addTarget(targetCreature);
        this.getSpellAbility().addTarget(targetPlayer);

        this.addInfo("Info1", "<i>(Do both if {U}{B} was spent.)</i>");
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
    }

    public RiversGrasp(final RiversGrasp card) {
        super(card);
    }

    @Override
    public RiversGrasp copy() {
        return new RiversGrasp(this);
    }
}

class RiversGraspEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public RiversGraspEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals his or her hand, you choose a card from it, then that player discards that card.";
    }

    public RiversGraspEffect(final RiversGraspEffect effect) {
        super(effect);
    }

    @Override
    public RiversGraspEffect copy() {
        return new RiversGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player != null) {
            player.revealCards("River's Grasp", player.getHand(), game);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                TargetCard target = new TargetCard(Zone.PICK, filter);
                if (controller.choose(Outcome.Benefit, player.getHand(), target, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        return player.discard(card, source, game);
                    }
                }
            }
        }
        return false;
    }
}
