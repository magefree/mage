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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Styxo
 */
public class JediTraining extends CardImpl {

    protected static final FilterCard filter = new FilterCard("Jedi spells");

    static {
        filter.add(new SubtypePredicate("Jedi"));
    }

    public JediTraining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Jedi spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Meditate costs you pay cost {1} less.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbilitiesCostReductionControllerEffect(MeditateAbility.class, "Meditate")));

        // Whenever a Jedi creature you control meditates, scry 1.
        this.addAbility(new JediTrainingTriggeredAbility());
    }

    public JediTraining(final JediTraining card) {
        super(card);
    }

    @Override
    public JediTraining copy() {
        return new JediTraining(this);
    }
}

class JediTrainingTriggeredAbility extends TriggeredAbilityImpl {

    public JediTrainingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScryEffect(1));
    }

    public JediTrainingTriggeredAbility(final JediTrainingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JediTrainingTriggeredAbility copy() {
        return new JediTrainingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.MEDITATED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {        
        Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
        game.informPlayers("3 " + source.getLogName());
        if (event.getPlayerId().equals(getControllerId()) && source != null && JediTraining.filter.match(source, game)) {
            return true;
        }
        return false;
    }

    public String getRule() {
        return "Whenever a Jedi creature you control meditates, scry 1";
    }
}
