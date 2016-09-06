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
package mage.sets.futuresight;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class DaybreakCoronet extends CardImpl {
    
    public DaybreakCoronet(UUID ownerId) {
        super(ownerId, 21, "Daybreak Coronet", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Aura");


        // Enchant creature with another Aura attached to it
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with another Aura attached to it.");
        filter.add(new AuraAttachedPredicate(this.getId()));
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature gets +3/+3 and has first strike, vigilance, and lifelink.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has first strike");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public DaybreakCoronet(final DaybreakCoronet card) {
        super(card);
    }

    @Override
    public DaybreakCoronet copy() {
        return new DaybreakCoronet(this);
    }
}

class AuraAttachedPredicate implements Predicate<Permanent> {
    
    private final UUID ownId;
    
    public AuraAttachedPredicate(UUID ownId) {
        this.ownId = ownId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        List<UUID> attachments = new LinkedList();
        attachments.addAll(input.getAttachments());
        for (UUID uuid : attachments) {
            if (!uuid.equals(ownId)) {
                Permanent attachment = game.getPermanent(uuid);
                if (attachment != null
                        && attachment.getSubtype(game).contains("Aura")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Aura attached";
    }
}
