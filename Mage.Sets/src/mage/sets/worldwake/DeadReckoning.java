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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DeadReckoning extends CardImpl<DeadReckoning> {

    public DeadReckoning(UUID ownerId) {
        super(ownerId, 56, "Dead Reckoning", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "WWK";

        this.color.setBlack(true);

        // You may put target creature card from your graveyard on top of your library. If you do, Dead Reckoning deals damage equal to that card's power to target creature.
        this.getSpellAbility().addEffect(new DeadReckoningEffect());

    }

    public DeadReckoning(final DeadReckoning card) {
        super(card);
    }

    @Override
    public DeadReckoning copy() {
        return new DeadReckoning(this);
    }
}

class DeadReckoningEffect extends OneShotEffect<DeadReckoningEffect> {

    public DeadReckoningEffect() {
        super(Constants.Outcome.Damage);
        this.staticText = "You may put target creature card from your graveyard on top of your library. If you do, {this} deals damage equal to that card's power to target creature";
    }

    public DeadReckoningEffect(final DeadReckoningEffect effect) {
        super(effect);
    }

    @Override
    public DeadReckoningEffect copy() {
        return new DeadReckoningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        TargetCardInYourGraveyard target1 = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent();

        if (you != null) {
            if (target1.canChoose(source.getControllerId(), game)
                    && you.choose(Constants.Outcome.Benefit, target1, source.getSourceId(), game)
                    && target2.canChoose(source.getControllerId(), game)
                    && you.choose(Constants.Outcome.Damage, target2, source.getSourceId(), game)) {
                Card creatureInGraveyard = game.getCard(target1.getFirstTarget());
                if (creatureInGraveyard != null) {
                    if (creatureInGraveyard.moveToZone(Constants.Zone.LIBRARY, id, game, true)) {
                        int power = creatureInGraveyard.getPower().getValue();
                        Permanent creature = game.getPermanent(target2.getFirstTarget());
                        if (creature != null) {
                            creature.damage(power, id, game, true, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
