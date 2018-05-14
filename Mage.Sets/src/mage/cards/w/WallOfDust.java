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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J (based on LevelX2)
 */
public class WallOfDust extends CardImpl {

    public WallOfDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Dust blocks a creature, that creature can't attack during its controller's next turn.
        this.addAbility(new BlocksTriggeredAbility(new WallOfDustRestrictionEffect(), false, true));
    }

    public WallOfDust(final WallOfDust card) {
        super(card);
    }

    @Override
    public WallOfDust copy() {
        return new WallOfDust(this);
    }
}

class WallOfDustRestrictionEffect extends RestrictionEffect {

    int nextTurnTargetController = 0;
    protected MageObjectReference targetPermanentReference;

    public WallOfDustRestrictionEffect() {
        super(Duration.Custom);
        staticText = "that creature can't attack during its controller's next turn";
    }

    public WallOfDustRestrictionEffect(final WallOfDustRestrictionEffect effect) {
        super(effect);
        this.nextTurnTargetController = effect.nextTurnTargetController;
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public WallOfDustRestrictionEffect copy() {
        return new WallOfDustRestrictionEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (targetPermanentReference == null) {
            return true;
        }
        Permanent targetPermanent = targetPermanentReference.getPermanent(game);
        if (targetPermanent == null) {
            return true;
        }
        if (nextTurnTargetController == 0 && startingTurn != game.getTurnNum() && game.getActivePlayerId().equals(targetPermanent.getControllerId())) {
            nextTurnTargetController = game.getTurnNum();
        }
        return game.getPhase().getType() == TurnPhase.END && nextTurnTargetController > 0 && game.getTurnNum() > nextTurnTargetController;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getTargetPointer().getFirst(game, source) == null) {
            discard();
        } else {
            targetPermanentReference = new MageObjectReference(getTargetPointer().getFirst(game, source), game);
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
            if (game.getActivePlayerId().equals(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    public boolean canAttack(Game game) {
        return false;
    }
}
