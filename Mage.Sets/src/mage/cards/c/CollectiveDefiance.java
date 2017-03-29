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
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.PlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class CollectiveDefiance extends CardImpl {

    private static final FilterPlayer filterDiscard = new FilterPlayer("player to discard and then draw cards");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature to be dealt damage");
    private static final FilterPlayer filterDamageOpponent = new FilterPlayer("opponent to be dealt damage");

    static {
        filterDamageOpponent.add(new PlayerPredicate(TargetController.OPPONENT));
    }

    public CollectiveDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");

        // Escalate {1}
        this.addAbility(new EscalateAbility(new GenericManaCost(1)));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target player discards all cards in his or her hand, then draws that many cards.;
        this.getSpellAbility().addEffect(new CollectiveDefianceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterDiscard));

        // Collective Defiance deals 4 damage to target creature.;
        Mode mode = new Mode();
        Effect effect = new DamageTargetEffect(4);
        effect.setText("{this} deals 4 damage to target creature");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(filterCreature));
        this.getSpellAbility().addMode(mode);

        // Collective Defiance deals 3 damage to target opponent.
        mode = new Mode();
        effect = new DamageTargetEffect(3);
        effect.setText("{this} deals 3 damage to target opponent");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetPlayer(1, 1, false, filterDamageOpponent));
        this.getSpellAbility().addMode(mode);
    }

    public CollectiveDefiance(final CollectiveDefiance card) {
        super(card);
    }

    @Override
    public CollectiveDefiance copy() {
        return new CollectiveDefiance(this);
    }
}

class CollectiveDefianceEffect extends OneShotEffect {

    public CollectiveDefianceEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards all the cards in his or her hand, then draws that many cards";
    }

    public CollectiveDefianceEffect(final CollectiveDefianceEffect effect) {
        super(effect);
    }

    @Override
    public CollectiveDefianceEffect copy() {
        return new CollectiveDefianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                int count = targetPlayer.getHand().size();
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    targetPlayer.discard(card, source, game);
                }
                targetPlayer.drawCards(count, game);
                return false;
            }
        return true;
    }
}