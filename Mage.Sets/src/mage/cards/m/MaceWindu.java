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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author Styxo
 */
public class MaceWindu extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public MaceWindu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Jedi");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Mace Windu enters the battlefield, return target spell or creature you don't control to its owner's hand.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetSpellOrPermanent(1, 1, filter, false));
        this.addAbility(ability);

        // Meditate {1}{U}
        this.addAbility(new MeditateAbility(new ManaCostsImpl("{1}{U}")));
    }

    public MaceWindu(final MaceWindu card) {
        super(card);
    }

    @Override
    public MaceWindu copy() {
        return new MaceWindu(this);
    }
}
