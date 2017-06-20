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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Quercitron
 */
public class RakdosTheDefiler extends CardImpl {

    private static final FilterPermanent attackTriggerFilter = new FilterControlledPermanent("the non-Demon permanents you control");
    private static final FilterPermanent damageToPlayerTriggerFilter = new FilterPermanent("the non-Demon permanents you control");

    static {
        attackTriggerFilter.add(Predicates.not(new SubtypePredicate(SubType.DEMON)));
        damageToPlayerTriggerFilter.add(Predicates.not(new SubtypePredicate(SubType.DEMON)));
    }

    public RakdosTheDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Demon");
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Rakdos the Defiler attacks, sacrifice half the non-Demon permanents you control, rounded up.
        Effect effect = new SacrificeControllerEffect(attackTriggerFilter, new HalfValue(new PermanentsOnBattlefieldCount(attackTriggerFilter), true), "");
        effect.setText("sacrifice half the non-Demon permanents you control, rounded up");
        Ability ability = new AttacksTriggeredAbility(effect, false);
        this.addAbility(ability);

        // Whenever Rakdos deals combat damage to a player, that player sacrifices half the non-Demon permanents he or she controls, rounded up.
        effect = new SacrificeEffect(damageToPlayerTriggerFilter, new HalfValue(new PermanentsTargetOpponentControlsCount(damageToPlayerTriggerFilter), true), "");
        effect.setText("that player sacrifices half the non-Demon permanents he or she controls, rounded up");
        ability = new DealsDamageToAPlayerTriggeredAbility(effect, false, true);
        this.addAbility(ability);
    }

    public RakdosTheDefiler(final RakdosTheDefiler card) {
        super(card);
    }

    @Override
    public RakdosTheDefiler copy() {
        return new RakdosTheDefiler(this);
    }
}
