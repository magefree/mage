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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DarksteelMutation extends CardImpl<DarksteelMutation> {

    public DarksteelMutation(UUID ownerId) {
        super(ownerId, 9, "Darksteel Mutation", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "C13";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature is a 0/1 Insect artifact creature with indestructible and loses all other abilities, card types, and creature types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new BecomesCreatureAttachedEffect(new DarksteelMutationInsectToken(),
                "Enchanted creature is a 0/1 Insect artifact creature with indestructible and loses all other abilities, card types, and creature types", 
                Duration.WhileOnBattlefield, true)));

    }

    public DarksteelMutation(final DarksteelMutation card) {
        super(card);
    }

    @Override
    public DarksteelMutation copy() {
        return new DarksteelMutation(this);
    }
}

class DarksteelMutationInsectToken extends Token {

    public DarksteelMutationInsectToken() {
        super("Insect", "0/1 Insect artifact creature with indestructible");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Insect");
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(IndestructibleAbility.getInstance());
    }

}