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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public class WellOfIdeas extends CardImpl {

    public WellOfIdeas(UUID ownerId) {
        super(ownerId, 20, "Well of Ideas", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");
        this.expansionSetCode = "C14";


        // When Well of Ideas enters the battlefield, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2), false));

        // At the beginning of each other player's draw step, that player draws an additional card.
        Effect effect  = new DrawCardTargetEffect(1);
        effect.setText("that player draws an additional card");
        this.addAbility(new BeginningOfDrawTriggeredAbility(effect, TargetController.NOT_YOU, false));

        // At the beginning of your draw step, draw two additional cards.
        effect  = new DrawCardTargetEffect(2);
        effect.setText("draw two additional cards");
        this.addAbility(new BeginningOfDrawTriggeredAbility(effect , TargetController.YOU, false));
    }

    public WellOfIdeas(final WellOfIdeas card) {
        super(card);
    }

    @Override
    public WellOfIdeas copy() {
        return new WellOfIdeas(this);
    }
}
