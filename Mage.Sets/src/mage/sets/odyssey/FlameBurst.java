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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LoneFox
 */
public class FlameBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(new NamePredicate("Flame Burst"),
            new AbilityPredicate(CountAsFlameBurstAbility.class)));
    }

    public FlameBurst(UUID ownerId) {
        super(ownerId, 194, "Flame Burst", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ODY";

        // Flame Burst deals X damage to target creature or player, where X is 2 plus the number of cards named Flame Burst in all graveyards.
        Effect effect = new DamageTargetEffect(new FlameBurstCount(filter));
        effect.setText("{this} deals X damage to target creature or player, where X is 2 plus the number of cards named Flame Burst in all graveyards.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public FlameBurst(final FlameBurst card) {
        super(card);
    }

    @Override
    public FlameBurst copy() {
        return new FlameBurst(this);
    }

    public static Ability getCountAsAbility() {
        return new CountAsFlameBurstAbility();
    }
}

class FlameBurstCount extends CardsInAllGraveyardsCount {

    public FlameBurstCount(FilterCard filter) {
        super(filter);
    }

    public FlameBurstCount(FlameBurstCount value) {
        super(value);
    }

    public FlameBurstCount copy() {
        return new FlameBurstCount(this);
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        return super.calculate(game, source, effect) + 2;
    }

}

class CountAsFlameBurstAbility extends SimpleStaticAbility {

    public CountAsFlameBurstAbility() {
        super(Zone.GRAVEYARD, new InfoEffect("If {this} is in a graveyard, effects from spells named Flame Burst count it as a card named Flame Burst"));
    }

    public CountAsFlameBurstAbility(CountAsFlameBurstAbility ability) {
        super(ability);
    }

    @Override
    public CountAsFlameBurstAbility copy() {
        return new CountAsFlameBurstAbility(this);
    }
}
