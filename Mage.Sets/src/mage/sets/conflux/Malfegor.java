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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Malfegor extends CardImpl<Malfegor> {

    public Malfegor(UUID ownerId) {
        super(ownerId, 117, "Malfegor", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{R}{R}");
        this.expansionSetCode = "CON";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Malfegor enters the battlefield, discard your hand. Each opponent sacrifices a creature for each card discarded this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MalfegorEffect(), false));

    }

    public Malfegor(final Malfegor card) {
        super(card);
    }

    @Override
    public Malfegor copy() {
        return new Malfegor(this);
    }
}

class MalfegorEffect extends OneShotEffect<MalfegorEffect> {

    public MalfegorEffect() {
        super(Outcome.Neutral);
        staticText = "discard your hand. Each opponent sacrifices a creature for each card discarded this way";
    }

    public MalfegorEffect(final MalfegorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int sacrificeNumber = controller.getHand().size();
            if (sacrificeNumber > 0) {
                controller.discard(sacrificeNumber, source, game);
                for (UUID opponentId : game.getOpponents(controller.getId())) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null) {
                        for (int i = 0; i < sacrificeNumber; i++) {
                            Target target = new TargetControlledPermanent(new FilterControlledCreaturePermanent());
                            target.setRequired(true);
                            if (target.canChoose(opponentId, game)) {
                                if (opponent.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                                    if (permanent != null) {
                                        permanent.sacrifice(source.getSourceId(), game);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public MalfegorEffect copy() {
        return new MalfegorEffect(this);
    }
}