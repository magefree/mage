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
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author TheElk801
 */
public class RelicRunner extends CardImpl {

    public RelicRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Relic Runner can't be blocked if you've cast an historic spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(new CantBeBlockedSourceAbility(), Duration.WhileOnBattlefield),
                        new CastHistoricSpellThisTurnCondition(),
                        "{this} can't be blocked if you've cast an historic spell this turn. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"
                )
        ), new SpellsCastWatcher());
    }

    public RelicRunner(final RelicRunner card) {
        super(card);
    }

    @Override
    public RelicRunner copy() {
        return new RelicRunner(this);
    }
}

class CastHistoricSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
        if (watcher != null) {
            List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
            if (spells != null) {
                for (Spell spell : spells) {
                    if (!spell.getSourceId().equals(source.getSourceId()) && spell.isHistoric()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
