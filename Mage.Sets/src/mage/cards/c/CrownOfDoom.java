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

import java.util.Objects;
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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CrownOfDoom extends CardImpl {

    private UUID abilityId;

    public CrownOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever a creature attacks you or a planeswalker you control, it gets +2/+0 until end of turn.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, new FilterCreaturePermanent(), SetTargetPointer.PERMANENT, true));

        // {2}: Target player other than Crown of Doom's owner gains control of it. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new CrownOfDoomEffect(), new ManaCostsImpl("{2}"), MyTurnCondition.getInstance());
        ability.addTarget(new TargetPlayer(1, 1, false, new FilterPlayer("player other than Crown of Doom's owner")));
        abilityId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(abilityId)) {
            Card sourceCard = game.getCard(ability.getSourceId());
            if (sourceCard != null) {
                ability.getTargets().clear();
                FilterPlayer filter = new FilterPlayer("player other than " + sourceCard.getIdName() + "'s owner");
                filter.add(Predicates.not(new PlayerIdPredicate(sourceCard.getOwnerId())));
                ability.addTarget(new TargetPlayer(1, 1, false, filter));
            }
        }
    }

    public CrownOfDoom(final CrownOfDoom card) {
        super(card);
        this.abilityId = card.abilityId;
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
        if (controller != null && newController != null && !Objects.equals(controller.getId(), newController.getId())) {
            // Duration.Custom = effect ends if Artifact leaves the current zone (battlefield)
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
            effect.setTargetPointer(new FixedTarget(source.getSourceId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
