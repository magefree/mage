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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class Chromanticore extends CardImpl<Chromanticore> {

    public Chromanticore(UUID ownerId) {
        super(ownerId, 144, "Chromanticore", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Manticore");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Bestow {2}{W}{U}{B}{R}{G}
        this.addAbility(new BestowAbility(this, "{2}{W}{U}{B}{R}{G}"));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Enchanted creature gets +4/+4 and has flying, first strike, vigilance, trample and lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4,4));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has flying");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", first strike");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);        
        
    }

    public Chromanticore(final Chromanticore card) {
        super(card);
    }

    @Override
    public Chromanticore copy() {
        return new Chromanticore(this);
    }
}
