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
package mage.sets.commander2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class KalemneDiscipleOfIroas extends CardImpl {
    
    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with converted mana cost 5 or greater");

    static {
        filterSpell.add(new CardTypePredicate(CardType.CREATURE));
		filterSpell.add(new ConvertedManaCostPredicate(Filter.ComparisonType.GreaterThan, 4));
    }

    public KalemneDiscipleOfIroas(UUID ownerId) {
        super(ownerId, 999, "Kalemne, Disciple of Iroas", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.expansionSetCode = "C15";
        this.supertype.add("Legendary");
        this.subtype.add("Giant");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Whenever you cast a creature spell with converted mana cost 5 or greater, you get an experience counter.
        Effect effect = new AddCountersControllerEffect(CounterType.EXPERIENCE.createInstance(1), false);
        effect.setText("you get an experience counter");
        Ability ability = new SpellCastControllerTriggeredAbility(effect, filterSpell, false);
        this.addAbility(ability);
        
        // Kalemne, Disciple of Iroas gets +1/+1 for each experience counter you have.
        DynamicValue value = new SourceControllerExperienceCountersCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)));
    }

    public KalemneDiscipleOfIroas(final KalemneDiscipleOfIroas card) {
        super(card);
    }

    @Override
    public KalemneDiscipleOfIroas copy() {
        return new KalemneDiscipleOfIroas(this);
    }
}

class SourceControllerExperienceCountersCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
                amount = player.getCounters().getCount(CounterType.EXPERIENCE);
            }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new SourceControllerExperienceCountersCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "experience counter you have";
    }
}
