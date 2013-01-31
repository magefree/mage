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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continious.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SoulRansom extends CardImpl<SoulRansom> {

    public SoulRansom(UUID ownerId) {
        super(ownerId, 198, "Soul Ransom", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Aura");

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ControlEnchantedEffect()));
        
        // Discard two cards: Soul Ransom's controller sacrifices it, then draws two cards. Only any opponent may activate this ability.
        Effect effect = new SacrificeSourceEffect();
        effect.setText("{this}'s controller sacrifices it.");
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, effect , new DiscardTargetCost(new TargetCardInHand(2,2, new FilterCard("two cards"))));
        effect = new DrawCardControllerEffect(2);
        effect.setText("Then draws two cards. Only any opponent may activate this ability");
        ability2.addEffect(effect);
        ability2.setOnlyOpponentCanActivate(true);
        this.addAbility(ability2);

    }

    public SoulRansom(final SoulRansom card) {
        super(card);
    }

    @Override
    public SoulRansom copy() {
        return new SoulRansom(this);
    }
}
