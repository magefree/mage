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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class FormOfTheDinosaur extends CardImpl {

    public FormOfTheDinosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");

        // When Form of the Dinosaur enters the battlefield, your life total becomes 15.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SetPlayerLifeSourceEffect(15), false));

        // At the beginning of your upkeep, Form of the Dinosaur deals 15 damage to target creature an opponent controls and that creature deals damage equal to its power to you.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new FormOfTheDinosaurEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    public FormOfTheDinosaur(final FormOfTheDinosaur card) {
        super(card);
    }

    @Override
    public FormOfTheDinosaur copy() {
        return new FormOfTheDinosaur(this);
    }
}

class FormOfTheDinosaurEffect extends OneShotEffect {

    public FormOfTheDinosaurEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 15 damage to target creature an opponent controls and that creature deals damage equal to its power to you";
    }

    public FormOfTheDinosaurEffect(final FormOfTheDinosaurEffect effect) {
        super(effect);
    }

    @Override
    public FormOfTheDinosaurEffect copy() {
        return new FormOfTheDinosaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(15, source.getSourceId(), game, false, true);
                controller.damage(targetCreature.getPower().getValue(), targetCreature.getId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
