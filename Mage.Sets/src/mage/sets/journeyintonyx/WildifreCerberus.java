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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class WildifreCerberus extends CardImpl<WildifreCerberus> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public WildifreCerberus(UUID ownerId) {
        super(ownerId, 116, "Wildifre Cerberus", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Hound");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {5}{R}{R}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{5}{R}{R}", 1));

        // When Wildfire Cerberus becomes monstrous, it deals 2 damage to each opponent and each creature your opponents control.
        Effect effect = new DamagePlayersEffect(2, TargetController.OPPONENT);
        effect.setText("it deals 2 damage to each opponent");
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(effect);
        effect = new DamageAllEffect(2, filter);
        effect.setText("and each creature your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public WildifreCerberus(final WildifreCerberus card) {
        super(card);
    }

    @Override
    public WildifreCerberus copy() {
        return new WildifreCerberus(this);
    }
}
