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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GiltLeafWinnower extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Elf creature whose power and toughness aren't equal");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Elf")));
        filter.add(new PowerToughnessNotEqualPredicate());
    }

    public GiltLeafWinnower(UUID ownerId) {
        super(ownerId, 99, "Gilt-Leaf Winnower", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ORI";
        this.subtype.add("Elf");
        this.subtype.add("Warrior");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Gilt-Leaf Winnower enters the battlefield, you may destroy target non-Elf creature whose power and toughness aren't equal.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public GiltLeafWinnower(final GiltLeafWinnower card) {
        super(card);
    }

    @Override
    public GiltLeafWinnower copy() {
        return new GiltLeafWinnower(this);
    }
}

class PowerToughnessNotEqualPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getPower().getValue() != input.getToughness().getValue();
    }

    @Override
    public String toString() {
        return "power and toughness aren't equal";
    }
}
