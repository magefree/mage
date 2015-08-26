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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class VorinclexVoiceOfHunger extends CardImpl {

    public VorinclexVoiceOfHunger(UUID ownerId) {
        super(ownerId, 127, "Vorinclex, Voice of Hunger", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.expansionSetCode = "NPH";
        this.supertype.add("Legendary");
        this.subtype.add("Praetor");

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        ManaEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("add one mana to your mana pool of any type that land produced");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect, new FilterControlledLandPermanent("you tap a land"),
                SetTargetPointer.PERMANENT));

        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        this.addAbility(new VorinclexTriggeredAbility2());
    }

    public VorinclexVoiceOfHunger(final VorinclexVoiceOfHunger card) {
        super(card);
    }

    @Override
    public VorinclexVoiceOfHunger copy() {
        return new VorinclexVoiceOfHunger(this);
    }
}

class VorinclexTriggeredAbility2 extends TriggeredAbilityImpl {

    private static final String staticText = "Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.";

    public VorinclexTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepTargetEffect());
    }

    public VorinclexTriggeredAbility2(VorinclexTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public VorinclexTriggeredAbility2 copy() {
        return new VorinclexTriggeredAbility2(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
