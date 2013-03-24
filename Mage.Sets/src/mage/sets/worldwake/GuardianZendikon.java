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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continious.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class GuardianZendikon extends CardImpl<GuardianZendikon> {

    public GuardianZendikon(UUID ownerId) {
        super(ownerId, 6, "Guardian Zendikon", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant land
        // Enchanted land is a 2/6 white Wall creature with defender. It's still a land.
        // When enchanted land dies, return that card to its owner's hand.

        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        Ability ability2 = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(new WallToken(), "Enchanted land is a 2/6 white wall creature with defender. It's still a land", Constants.Duration.Custom));
        this.addAbility(ability2);

        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false, false);
        this.addAbility(ability3);
    }

    public GuardianZendikon(final GuardianZendikon card) {
        super(card);
    }

    @Override
    public GuardianZendikon copy() {
        return new GuardianZendikon(this);
    }
}

class WallToken extends Token {

    WallToken() {
        super("", "2/6 white wall creature with defender");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Wall");
        power = new MageInt(2);
        toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
    }
}