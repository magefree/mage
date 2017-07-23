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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 * @author LevelX2
 */
public class SakashimaTheImpostor extends CardImpl {

    public SakashimaTheImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may have Sakashima the Impostor enter the battlefield as a copy of any creature on the battlefield, except its name is still Sakashima the Impostor, it's legendary in addition to its other types, and it gains "{2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step."
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new SakashimaTheImpostorApplier());
        effect.setText("as a copy of any creature on the battlefield, except its name is still Sakashima the Impostor, it's legendary in addition to its other types, and it gains \"{2}{U}{U}: Return {this} to its owner's hand at the beginning of the next end step.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    public SakashimaTheImpostor(final SakashimaTheImpostor card) {
        super(card);
    }

    @Override
    public SakashimaTheImpostor copy() {
        return new SakashimaTheImpostor(this);
    }
}

class SakashimaTheImpostorApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        permanent.addSuperType(SuperType.LEGENDARY);

        permanent.setName("Sakashima the Impostor");
        // {2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step
        permanent.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandSourceEffect(true)), false),
                new ManaCostsImpl("{2}{U}{U}")
        ), game);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        mageObject.addSuperType(SuperType.LEGENDARY);
        mageObject.setName("Sakashima the Impostor");
        // {2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step
        mageObject.getAbilities().add(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandSourceEffect(true)), false),
                new ManaCostsImpl("{2}{U}{U}")
        ));
        return true;
    }

}
