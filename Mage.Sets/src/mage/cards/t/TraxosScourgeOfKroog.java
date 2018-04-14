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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author TheElk801
 */
public class TraxosScourgeOfKroog extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(new HistoricPredicate());
    }

    public TraxosScourgeOfKroog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Traxos, Scourge of Kroog enters the battlefield tapped and doesn't untap during your untap step.
        Ability ability = new EntersBattlefieldTappedAbility(
                "{this} enters the battlefield tapped and doesn't untap during your untap step.");
        ability.addEffect(new DontUntapInControllersUntapStepSourceEffect());
        this.addAbility(ability);
        // Whenever you cast a historic spell untap Traxos.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, true));
    }

    public TraxosScourgeOfKroog(final TraxosScourgeOfKroog card) {
        super(card);
    }

    @Override
    public TraxosScourgeOfKroog copy() {
        return new TraxosScourgeOfKroog(this);
    }
}
