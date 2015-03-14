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
package mage.sets.nemesis;

import java.util.UUID;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jason E. Wall

 */
public class TreetopBracers extends CardImpl {

    public TreetopBracers(UUID ownerId) {
        super(ownerId, 123, "Treetop Bracers", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "NMS";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and can't be blocked except by creatures with flying.
        this.addAbility(new TreetopBracersAbility());
    }

    public TreetopBracers(final TreetopBracers card) {
        super(card);
    }

    @Override
    public TreetopBracers copy() {
        return new TreetopBracers(this);
    }
}

class TreetopBracersAbility extends StaticAbility {
    private static FilterCreaturePermanent onlyFlyingCreatures = new FilterCreaturePermanent("except by creatures with flying");

    static {
        onlyFlyingCreatures.add(new AbilityPredicate(FlyingAbility.class));
    }

    public TreetopBracersAbility() {
        super(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Effect cantBeBlocked = new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, onlyFlyingCreatures, AttachmentType.AURA);
        cantBeBlocked.setText("and can't be blocked except by creatures with flying.");
    }

    public TreetopBracersAbility(TreetopBracersAbility ability) {
        super(ability);
    }

    /**
     * Creates a fresh copy of this ability.
     *
     * @return A new copy of this ability.
     */
    @Override
    public TreetopBracersAbility copy() {
        return new TreetopBracersAbility(this);
    }
}
