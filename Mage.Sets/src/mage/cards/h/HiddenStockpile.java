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
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.ServoToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

/**
 *
 * @author LevelX2
 */
public class HiddenStockpile extends CardImpl {

    public HiddenStockpile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");

        // <i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, create a 1/1 colorless Servo artifact creature token.
        Ability ability = new ConditionalTriggeredAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new ServoToken()), false), RevoltCondition.instance,
                "<i>Revolt</i> &mdash; At the beginning of your end step, if a permanent you controlled left the battlefield this turn, create a 1/1 colorless Servo artifact creature token");
        ability.setAbilityWord(AbilityWord.REVOLT);
        ability.addWatcher(new RevoltWatcher());
        this.addAbility(ability);

        // {1}, Sacrifice a creature: Scry 1.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("a creature"))));
        this.addAbility(ability);
    }

    public HiddenStockpile(final HiddenStockpile card) {
        super(card);
    }

    @Override
    public HiddenStockpile copy() {
        return new HiddenStockpile(this);
    }
}
