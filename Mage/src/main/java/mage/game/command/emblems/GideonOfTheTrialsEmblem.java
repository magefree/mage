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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;

/**
 *
 * @author spjspj
 */
public class GideonOfTheTrialsEmblem extends Emblem {

    public GideonOfTheTrialsEmblem() {
        this.setName("Emblem - Gideon of the Trials");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new GideonOfTheTrialsCantLoseEffect());
        this.getAbilities().add(ability);
    }
}

class GideonOfTheTrialsCantLoseEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("a Gideon planeswalker");

    static {
        filter.add(new SubtypePredicate("Gideon"));
    }

    public GideonOfTheTrialsCantLoseEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = "As long as you control a Gideon planeswalker, you can't lose the game and your opponents can't win the game";
    }

    public GideonOfTheTrialsCantLoseEffect(final GideonOfTheTrialsCantLoseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == GameEvent.EventType.WINS && game.getOpponents(source.getControllerId()).contains(event.getPlayerId()))
                || (event.getType() == GameEvent.EventType.LOSES && event.getPlayerId().equals(source.getControllerId()))) {
            if (game.getBattlefield().contains(filter, source.getControllerId(), 1, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GideonOfTheTrialsCantLoseEffect copy() {
        return new GideonOfTheTrialsCantLoseEffect(this);
    }
}
