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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LoneFox
 */
public class KithkinMourncaller extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("an attacking Kithkin or Elf");

    static {
        filter.add(Predicates.or(new SubtypePredicate("Kithkin"), new SubtypePredicate("Elf")));
    }

    public KithkinMourncaller(UUID ownerId) {
        super(ownerId, 224, "Kithkin Mourncaller", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Kithkin");
        this.subtype.add("Scout");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an attacking Kithkin or Elf is put into your graveyard from the battlefield, you may draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DrawCardSourceControllerEffect(1),
            true, filter, false, true));
    }

    public KithkinMourncaller(final KithkinMourncaller card) {
        super(card);
    }

    @Override
    public KithkinMourncaller copy() {
        return new KithkinMourncaller(this);
    }
}
