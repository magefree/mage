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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterEnchantment;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class UmbraMystic extends CardImpl<UmbraMystic> {

    private static final FilterEnchantment filter = new FilterEnchantment("Auras attached to permanents you control");

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public UmbraMystic(UUID ownerId) {
        super(ownerId, 52, "Umbra Mystic", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Auras attached to permanents you control have totem armor.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new TotemArmorAbility(), Duration.WhileOnBattlefield, filter, false)));
    }

    public UmbraMystic(final UmbraMystic card) {
        super(card);
    }

    @Override
    public UmbraMystic copy() {
        return new UmbraMystic(this);
    }
}

class UmbraMysticPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {

    @Override
    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent attachement = input.getObject();
        if (attachement != null) {
            Permanent permanent = game.getPermanent(attachement.getAttachedTo());
            if (permanent != null && permanent.getControllerId().equals(input.getPlayerId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Attached to permanents you control";
    }
}
