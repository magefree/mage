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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class DevourFlesh extends CardImpl<DevourFlesh> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static{
        filter.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }

    public DevourFlesh (UUID ownerId) {
        super(ownerId, 63, "Devour Flesh", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlack(true);

        // Target player sacrifices a creature, then gains life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new DevourFleshSacrificeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public DevourFlesh(final DevourFlesh card) {
        super(card);
    }

    @Override
    public DevourFlesh  copy() {
        return new DevourFlesh(this);
    }
}

class DevourFleshSacrificeEffect extends OneShotEffect<DevourFleshSacrificeEffect> {

    public DevourFleshSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices a creature, then gains life equal to that creature's toughness";
    }

    public DevourFleshSacrificeEffect(final DevourFleshSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public DevourFleshSacrificeEffect copy() {
        return new DevourFleshSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(player.getId()));
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        if (realCount > 0) {
            Target target = new TargetControlledPermanent(1, 1, filter, true);
            target.setRequired(true);
            while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int gainLife = permanent.getToughness().getValue();
                permanent.sacrifice(source.getSourceId(), game);
                player.gainLife(gainLife, game);
            } else{
                return false;
            }
        }
        return true;
    }
}
