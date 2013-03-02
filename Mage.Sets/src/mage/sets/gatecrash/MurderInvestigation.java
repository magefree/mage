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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class MurderInvestigation extends CardImpl<MurderInvestigation> {

    public MurderInvestigation(UUID ownerId) {
        super(ownerId, 21, "Murder Investigation", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "GTC";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);


        // When enchanted creature dies, put X 1/1 white Soldier creature tokens onto the battlefield, where X is its power.
        this.addAbility(new DiesAttachedTriggeredAbility(new CreateTokenEffect(new SoldierToken(), new AttachedPermanentPowerCount()), "enchanted creature"));
    }

    public MurderInvestigation(final MurderInvestigation card) {
        super(card);
    }

    @Override
    public MurderInvestigation copy() {
        return new MurderInvestigation(this);
    }
}

class AttachedPermanentPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Permanent attachmentPermanent = game.getPermanent(sourceAbility.getSourceId());
        if (attachmentPermanent == null) {
            attachmentPermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD);
        }
        if (attachmentPermanent != null && attachmentPermanent.getAttachedTo() != null) {
            Permanent attached = game.getPermanent(attachmentPermanent.getAttachedTo());
            if (attached == null) {
                attached = (Permanent) game.getLastKnownInformation(attachmentPermanent.getAttachedTo(), Zone.BATTLEFIELD);
            }
            if (attached != null) {
                return attached.getPower().getValue();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new AttachedPermanentPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "its power";
    }
}
