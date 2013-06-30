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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class ThundercloudShaman extends CardImpl<ThundercloudShaman> {

    private static final FilterCreaturePermanent filterGiants = new FilterCreaturePermanent("equal to the number of Giants you control");
    private static final FilterCreaturePermanent filterNonGiants = new FilterCreaturePermanent("non-Giant creature");
    static {
        filterGiants.add(new ControllerPredicate(TargetController.YOU));
        filterGiants.add(new SubtypePredicate("Giant"));
        filterNonGiants.add(Predicates.not(new SubtypePredicate("Giant")));
    }

    public ThundercloudShaman(UUID ownerId) {
        super(ownerId, 135, "Thundercloud Shaman", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Giant");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Thundercloud Shaman enters the battlefield, it deals damage equal to the number of Giants you control to each non-Giant creature.
        Effect effect = new DamageAllEffect(new PermanentsOnBattlefieldCount(filterGiants),filterNonGiants);
        effect.setText("it deals damage equal to the number of Giants you control to each non-Giant creature");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
    }

    public ThundercloudShaman(final ThundercloudShaman card) {
        super(card);
    }

    @Override
    public ThundercloudShaman copy() {
        return new ThundercloudShaman(this);
    }
}
