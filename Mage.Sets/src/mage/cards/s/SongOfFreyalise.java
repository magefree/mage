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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURES;

/**
 *
 * @author TheElk801
 */
public class SongOfFreyalise extends CardImpl {

    public SongOfFreyalise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I, II — Until your next turn, creatures you control gain "T: Add one mana of any color."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new GainAbilityControlledEffect(new AnyColorManaAbility(), Duration.UntilYourNextTurn, FILTER_CONTROLLED_CREATURES)
                        .setText("Until your next turn, creatures you control gain \"{T}: Add one mana of any color.\"")
        );

        // III — Put a +1/+1 counter on each creature you control. Those creatures gain vigilance, trample, and indestructible until end of turn.
        Ability ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new AddCountersAllEffect(CounterType.P1P1.createInstance(), FILTER_CONTROLLED_CREATURES));
        ability.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, FILTER_CONTROLLED_CREATURES)
                .setText("Those creatures gain vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, FILTER_CONTROLLED_CREATURES)
                .setText(", trample"));
        ability.addEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, FILTER_CONTROLLED_CREATURES)
                .setText("and indestructible until end of turn"));
        this.addAbility(sagaAbility);
    }

    public SongOfFreyalise(final SongOfFreyalise card) {
        super(card);
    }

    @Override
    public SongOfFreyalise copy() {
        return new SongOfFreyalise(this);
    }
}
