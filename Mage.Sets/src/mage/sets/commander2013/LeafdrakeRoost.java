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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
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
public class LeafdrakeRoost extends CardImpl<LeafdrakeRoost> {

    public LeafdrakeRoost(UUID ownerId) {
        super(ownerId, 196, "Leafdrake Roost", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}");
        this.expansionSetCode = "C13";
        this.subtype.add("Aura");

        this.color.setBlue(true);
        this.color.setGreen(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land has "{G}{U}, {tap}: Put a 2/2 green and blue Drake creature token with flying onto the battlefield."
        Ability abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new LeafdrakeRoostDragonToken()), new ManaCostsImpl("{G}{U}"));
        abilityToGain.addCost(new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(abilityToGain, AttachmentType.AURA, Duration.WhileOnBattlefield,
                "Enchanted land has \"{G}{U}, {t}: Put a 2/2 green and blue Drake creature token with flying onto the battlefield.\"")));

    }

    public LeafdrakeRoost(final LeafdrakeRoost card) {
        super(card);
    }

    @Override
    public LeafdrakeRoost copy() {
        return new LeafdrakeRoost(this);
    }
}

class LeafdrakeRoostDragonToken extends Token {

    public LeafdrakeRoostDragonToken() {
        super("Dragon", "2/2 green and blue Drake creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setBlue(true);
        subtype.add("Dragon");
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

}
