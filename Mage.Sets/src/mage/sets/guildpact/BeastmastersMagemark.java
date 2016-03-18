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
package mage.sets.guildpact;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.BlockedCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Markedagain
 */
public class BeastmastersMagemark extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control that are enchanted");

    static {
        filter.add(new EnchantedPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public BeastmastersMagemark(UUID ownerId) {
        super(ownerId, 80, "Beastmaster's Magemark", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Creatures you control that are enchanted get +1/+1.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        this.addAbility(ability);
        // Whenever a creature you control that's enchanted becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        BlockedCreatureCount value = new BlockedCreatureCount();
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +1/+1 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, filter, false));
    }

    public BeastmastersMagemark(final BeastmastersMagemark card) {
        super(card);
    }

    @Override
    public BeastmastersMagemark copy() {
        return new BeastmastersMagemark(this);
    }
}
