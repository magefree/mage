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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class SpawningGrounds extends CardImpl<SpawningGrounds> {

    public SpawningGrounds(UUID ownerId) {
        super(ownerId, 171, "Spawning Grounds", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{6}{G}{G}");
        this.expansionSetCode = "C13";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land has "{tap}: Put a 5/5 green Beast creature token with trample onto the battlefield."
        Ability abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SpawningGroundsBeastToken(), 1), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(abilityToGain, AttachmentType.AURA, Duration.WhileOnBattlefield,
                "Enchanted land has \"{t}: Put a 5/5 green Beast creature token with trample onto the battlefield.\"")));
    }

    public SpawningGrounds(final SpawningGrounds card) {
        super(card);
    }

    @Override
    public SpawningGrounds copy() {
        return new SpawningGrounds(this);
    }
}

class SpawningGroundsBeastToken extends Token {

    public SpawningGroundsBeastToken() {
        super("Beast", "5/5 green Beast creature token with trample");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Beast");
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(TrampleAbility.getInstance());
    }

}
