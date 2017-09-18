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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class StalkingYeti extends CardImpl {

    public StalkingYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stalking Yeti enters the battlefield, if it's on the battlefield, it deals damage equal to its power to target creature an opponent controls and that creature deals damage equal to its power to Stalking Yeti.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new StalkingYetiEffect()),
                SourceOnBattlefieldCondition.instance,
                "When {this} enters the battlefield, if it's on the battlefield, "
                + "it deals damage equal to its power to target creature an opponent controls "
                + "and that creature deals damage equal to its power to {this}."
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {2}{snow}: Return Stalking Yeti to its owner's hand. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl("{2}{S}")));
    }

    public StalkingYeti(final StalkingYeti card) {
        super(card);
    }

    @Override
    public StalkingYeti copy() {
        return new StalkingYeti(this);
    }
}

class StalkingYetiEffect extends OneShotEffect {

    StalkingYetiEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals damage equal to its power to target creature an opponent controls "
                + "and that creature deals damage equal to its power to {this}";
    }

    StalkingYetiEffect(final StalkingYetiEffect effect) {
        super(effect);
    }

    @Override
    public StalkingYetiEffect copy() {
        return new StalkingYetiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisCreature = game.getPermanent(source.getSourceId());
        Permanent thatCreature = game.getPermanent(source.getFirstTarget());
        if (thisCreature == null || thatCreature == null) {
            return false;
        }
        thatCreature.damage(thisCreature.getPower().getValue(), thisCreature.getId(), game, false, true);
        thisCreature.damage(thatCreature.getPower().getValue(), thatCreature.getId(), game, false, true);
        return true;
    }
}
