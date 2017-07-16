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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.CyclingDiscardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NewPerspectives extends CardImpl {

    public NewPerspectives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");

        // When New Perspectives enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3)));

        // As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NewPerspectivesCostModificationEffect()));
    }

    public NewPerspectives(final NewPerspectives card) {
        super(card);
    }

    @Override
    public NewPerspectives copy() {
        return new NewPerspectives(this);
    }
}

class NewPerspectivesCostModificationEffect extends CostModificationEffectImpl {

    NewPerspectivesCostModificationEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.SET_COST);
        this.staticText = "As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs";
    }

    NewPerspectivesCostModificationEffect(final NewPerspectivesCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        return controller != null
                && controller.getId().equals(source.getControllerId())
                && abilityToModify instanceof CyclingAbility
                && controller.getHand().size() >= 7;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            if ((abilityToModify instanceof ActivatedAbility && ((ActivatedAbility) abilityToModify).isCheckPlayableMode()) || controller.chooseUse(Outcome.PlayForFree, "Pay {0} to cycle?", source, game)) {
                abilityToModify.getCosts().clear();
                abilityToModify.getManaCostsToPay().clear();
                abilityToModify.getCosts().add(new CyclingDiscardCost());
            }
            return true;
        }
        return false;
    }

    @Override
    public NewPerspectivesCostModificationEffect copy() {
        return new NewPerspectivesCostModificationEffect(this);
    }
}
