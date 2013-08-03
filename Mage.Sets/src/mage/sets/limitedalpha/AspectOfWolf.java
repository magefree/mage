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
package mage.sets.limitedalpha;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author KholdFuzion

 */
public class AspectOfWolf extends CardImpl<AspectOfWolf> {

    public AspectOfWolf(UUID ownerId) {
        super(ownerId, 93, "Aspect of Wolf", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "LEA";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature gets +X/+Y, where X is half the number of Forests you control, rounded down, and Y is half the number of Forests you control, rounded up.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(new HalfForestsDownCount(), new  HalfForestsUpCount(), Duration.WhileOnBattlefield)));
    }

    public AspectOfWolf(final AspectOfWolf card) {
        super(card);
    }

    @Override
    public AspectOfWolf copy() {
        return new AspectOfWolf(this);
    }
}

class HalfForestsDownCount implements DynamicValue {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) / 2;
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new HalfForestsDownCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "half the number of Forests you control, rounded down";
    }
}

class HalfForestsUpCount implements DynamicValue {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = (int) Math.ceil(game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) / 2f);
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new HalfForestsUpCount();
    }

    @Override
    public String toString() {
        return "Y";
    }

    @Override
    public String getMessage() {
        return "half the number of Forests you control, rounded up";
    }
}
