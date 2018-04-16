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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author Will
 */
public class HelmOfTheHost extends CardImpl {

    public HelmOfTheHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);
        
        // At the beginning of combat on your turn, create a token that’s a copy of equipped creature, except the token isn’t legendary if equipped creature is legendary. That token gains haste.
        TriggeredAbility ability = new BeginningOfCombatTriggeredAbility(
                new HelmOfTheHostEffect(),
                TargetController.YOU,
                false
        );
        this.addAbility(ability);
        
        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(5)));
    }

    public HelmOfTheHost(final HelmOfTheHost card) {
        super(card);
    }

    @Override
    public HelmOfTheHost copy() {
        return new HelmOfTheHost(this);
    }
}

class HelmOfTheHostEffect extends OneShotEffect {
    public HelmOfTheHostEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a token that’s a copy of equipped creature, except the token isn’t legendary if equipped creature is legendary. That token gains haste.";
    }

    public HelmOfTheHostEffect(final HelmOfTheHostEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfTheHostEffect copy() {
        return new HelmOfTheHostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (equipment == null) {
            return true;
        }
        Permanent creature = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
        if (creature == null) {
            return true;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
        effect.setTargetPointer(new FixedTarget(creature, game));
        effect.setIsntLegendary(true);
        return effect.apply(game, source);
    }

}
