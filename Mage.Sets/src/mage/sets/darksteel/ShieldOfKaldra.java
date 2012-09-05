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
package mage.sets.darksteel;

import java.util.UUID;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.IndestructibleAllEffect;
import mage.abilities.effects.common.IndestructibleAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public class ShieldOfKaldra extends CardImpl<ShieldOfKaldra> {

    private static final FilterPermanent filter = new FilterPermanent("Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra");

    static {
        filter.add(new SubtypePredicate("Equipment"));
        filter.add(Predicates.or(
                new NamePredicate("Sword of Kaldra"),
                new NamePredicate("Shield of Kaldra"),
                new NamePredicate("Helm of Kaldra")));
    }

    public ShieldOfKaldra(UUID ownerId) {
        super(ownerId, 139, "Shield of Kaldra", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "DST";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra are indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IndestructibleAllEffect(filter)));
        // Equipped creature is indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IndestructibleAttachedEffect(AttachmentType.EQUIPMENT)));
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(4)));
    }

    public ShieldOfKaldra(final ShieldOfKaldra card) {
        super(card);
    }

    @Override
    public ShieldOfKaldra copy() {
        return new ShieldOfKaldra(this);
    }
}
