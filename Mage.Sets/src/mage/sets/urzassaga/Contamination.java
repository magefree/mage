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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class Contamination extends CardImpl {

    public Contamination(UUID ownerId) {
        super(ownerId, 123, "Contamination", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "USG";

        // At the beginning of your upkeep, sacrifice Contamination unless you sacrifice a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("a creature")))), TargetController.YOU, false));

        // If a land is tapped for mana, it produces {B} instead of any other type and amount.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ContaminationReplacementEffect()));
    }

    public Contamination(final Contamination card) {
        super(card);
    }

    @Override
    public Contamination copy() {
        return new Contamination(this);
    }
}

class ContaminationReplacementEffect extends ReplacementEffectImpl {

    ContaminationReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a land is tapped for mana, it produces {B} instead of any other type and amount";
    }

    ContaminationReplacementEffect(final ContaminationReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ContaminationReplacementEffect copy() {
        return new ContaminationReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.BlackMana);
        return false;
    }
    
    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }
    

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        return mageObject != null && mageObject.getCardType().contains(CardType.LAND);
    }
}
