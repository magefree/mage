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
package mage.sets.magic2010;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public class XathridDemon extends CardImpl<XathridDemon> {

    public XathridDemon(UUID ownerId) {
        super(ownerId, 122, "Xathrid Demon", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.expansionSetCode = "M10";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a creature other than Xathrid Demon, then each opponent loses life equal to the sacrificed creature's power. If you can't sacrifice a creature, tap Xathrid Demon and you lose 7 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new XathridDemonEffect(), TargetController.YOU, false));
    }

    public XathridDemon(final XathridDemon card) {
        super(card);
    }

    @Override
    public XathridDemon copy() {
        return new XathridDemon(this);
    }
}

class XathridDemonEffect extends OneShotEffect<XathridDemonEffect> {

    public XathridDemonEffect() {
        super(Outcome.Damage);
        this.staticText = "At the beginning of your upkeep, sacrifice a creature other than {this}, then each opponent loses life equal to the sacrificed creature's power. If you can't sacrifice a creature, tap {this} and you lose 7 life";
    }

    public XathridDemonEffect(final XathridDemonEffect effect) {
        super(effect);
    }

    @Override
    public XathridDemonEffect copy() {
        return new XathridDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (player == null || sourcePermanent == null) {
            return false;
        }

        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature other than " + sourcePermanent.getName());
        filter.setAnother(true);

        Target target = new TargetControlledCreaturePermanent(1, 1, filter, true, true);
        if (target.canChoose(source.getSourceId(), player.getId(), game)) {
            player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int amount = permanent.getPower().getValue();
                permanent.sacrifice(source.getSourceId(), game);

                if (amount > 0) {
                    Set<UUID> opponents = game.getOpponents(source.getControllerId());
                    for (UUID opponentId : opponents) {
                        Player opponent = game.getPlayer(opponentId);
                        if (opponent != null) {
                            opponent.loseLife(amount, game);
                        }
                    }
                }
                return true;
            }
        } else {
            sourcePermanent.tap(game);
            player.loseLife(7, game);
            return true;
        }
        return false;
    }
}
