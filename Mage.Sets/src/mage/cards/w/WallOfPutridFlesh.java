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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.MageObject;

/**
 *
 * @author Galatolol
 */
public class WallOfPutridFlesh extends CardImpl {

    public WallOfPutridFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add("Wall");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // Prevent all damage that would be dealt to Wall of Putrid Flesh by enchanted creatures.
        // The term “enchanted creatures” means “creatures with an Aura on them”.
        Effect effect = new PreventDamageToSourceByEnchantedCreatures();
        effect.setText("Prevent all damage that would be dealt to {this} by enchanted creatures.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public WallOfPutridFlesh(final WallOfPutridFlesh card) {
        super(card);
    }

    @Override
    public WallOfPutridFlesh copy() {
        return new WallOfPutridFlesh(this);
    }
}

class PreventDamageToSourceByEnchantedCreatures extends PreventAllDamageToSourceEffect {

    public PreventDamageToSourceByEnchantedCreatures(){
        super(Duration.WhileOnBattlefield);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (isEnchantedCreature(game.getObject(event.getSourceId()), game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEnchantedCreature(MageObject input, Game game) {
        if (input != null && !input.getCardType().contains(CardType.CREATURE)) {
            return false;
        }
        for (UUID attachmentId : ((Permanent)input).getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.getCardType().contains(CardType.ENCHANTMENT)) {
                return true;
            }
        }
        return false;
    }
}
