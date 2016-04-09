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
package mage.sets.antiquities;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public class TawnossCoffin extends CardImpl {

    public TawnossCoffin(UUID ownerId) {
        super(ownerId, 33, "Tawnos's Coffin", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "ATQ";

        // You may choose not to untap Tawnos's Coffin during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // {3}, {tap}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature. 
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl("{3}"));
        ability.addTarget(new TargetCreaturePermanent());        
        this.addAbility(ability);
        //When Tawnos's Coffin leaves the battlefield...
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD, true), false);
        this.addAbility(ability2);
        //or becomes untapped, return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent.
        Ability ability3 = new BecomesUnTappedSourceTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD, true), false);
        this.addAbility(ability3);
    }

    public TawnossCoffin(final TawnossCoffin card) {
        super(card);
    }

    @Override
    public TawnossCoffin copy() {
        return new TawnossCoffin(this);
    }
}
class BecomesUnTappedSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesUnTappedSourceTriggeredAbility(Effect effect, boolean isOptional) {
        super(Zone.BATTLEFIELD, effect, isOptional);
    }

    public BecomesUnTappedSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public BecomesUnTappedSourceTriggeredAbility(final BecomesUnTappedSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesUnTappedSourceTriggeredAbility copy() {
        return new BecomesUnTappedSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(sourceId);
    }

    @Override
    public String getRule() {
        return "When {this} becomes untapped, " + super.getRule();
    }
}