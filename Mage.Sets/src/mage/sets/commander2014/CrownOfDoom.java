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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.PlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CrownOfDoom extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer("player other than Crown of Doom's owner");

    static {
        filter.add(new PlayerPredicate(TargetController.NOT_YOU));
    }
    
    public CrownOfDoom(UUID ownerId) {
        super(ownerId, 55, "Crown of Doom", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "C14";

        // Whenever a creature attacks you or a planeswalker you control, it gets +2/+0 until end of turn.
        Effect effect = new BoostTargetEffect(2,0,Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, new FilterCreaturePermanent(), SetTargetPointer.PERMANENT, true));

        // {2}: Target player other than Crown of Doom's owner gains control of it. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new CrownOfDoomEffect(), new ManaCostsImpl("{2}"), MyTurnCondition.getInstance());
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
    }

    public CrownOfDoom(final CrownOfDoom card) {
        super(card);
    }

    @Override
    public CrownOfDoom copy() {
        return new CrownOfDoom(this);
    }
}

class CrownOfDoomEffect extends OneShotEffect {

    public CrownOfDoomEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target player other than {this}'s owner gains control of it";
    }

    public CrownOfDoomEffect(final CrownOfDoomEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfDoomEffect copy() {
        return new CrownOfDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && newController != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, newController.getId());
            effect.setTargetPointer(new FixedTarget(source.getSourceId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
