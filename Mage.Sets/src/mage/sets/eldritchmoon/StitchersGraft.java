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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnattachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeEquippedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;

/**
 *
 * @author spjspj
 */
public class StitchersGraft extends CardImpl {

    public StitchersGraft(UUID ownerId) {
        super(ownerId, 200, "Stitcher's Graft", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Equipment");

        // Equipped creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 3)));

        // Whenever equipped creature attacks, it doesn't untap during its controller's next untap step.
        Effect effect = new GainAbilityAttachedEffect(new StitchersGraftTriggeredAbility(), AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"Whenever equipped creature attacks, it doesn't untap during its controller's next untap step.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever Stitcher's Graft becomes unattached from a permanent, sacrifice that permanent.
        this.addAbility(new UnattachedTriggeredAbility(new SacrificeEquippedEffect(), false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public StitchersGraft(final StitchersGraft card) {
        super(card);
    }

    @Override
    public StitchersGraft copy() {
        return new StitchersGraft(this);
    }
}

class StitchersGraftTriggeredAbility extends TriggeredAbilityImpl {

    public StitchersGraftTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepSourceEffect());
    }

    public StitchersGraftTriggeredAbility(final StitchersGraftTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StitchersGraftTriggeredAbility copy() {
        return new StitchersGraftTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks, it doesn't untap during its controller's next untap step";
    }
}
