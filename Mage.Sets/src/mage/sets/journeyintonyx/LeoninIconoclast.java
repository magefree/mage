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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class LeoninIconoclast extends CardImpl<LeoninIconoclast> {

    private static final FilterPermanent filter = new FilterPermanent("enchantment creature an opponent controls");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public LeoninIconoclast(UUID ownerId) {
        super(ownerId, 16, "Leonin Iconoclast", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Cat");
        this.subtype.add("Monk");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Heroic — Whenever you cast a spell that targets Leonin Iconoclast, destroy target enchantment creature an opponent controls.
        Ability ability = new HeroicAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter, true));
        this.addAbility(ability);
    }

    public LeoninIconoclast(final LeoninIconoclast card) {
        super(card);
    }

    @Override
    public LeoninIconoclast copy() {
        return new LeoninIconoclast(this);
    }
}
