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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DreamspoilerWitches extends CardImpl<DreamspoilerWitches> {

    public DreamspoilerWitches(UUID ownerId) {
        super(ownerId, 81, "Dreamspoiler Witches", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell during an opponent's turn, you may have target creature get -1/-1 until end of turn.
        this.addAbility(new DreamspoilerWitchesTriggeredAbility());

    }

    public DreamspoilerWitches(final DreamspoilerWitches card) {
        super(card);
    }

    @Override
    public DreamspoilerWitches copy() {
        return new DreamspoilerWitches(this);
    }
}

class DreamspoilerWitchesTriggeredAbility extends TriggeredAbilityImpl<DreamspoilerWitchesTriggeredAbility> {
    DreamspoilerWitchesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-1,-1, Duration.EndOfTurn), true);
        this.addTarget(new TargetCreaturePermanent(true));
    }

    DreamspoilerWitchesTriggeredAbility(final DreamspoilerWitchesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DreamspoilerWitchesTriggeredAbility copy() {
        return new DreamspoilerWitchesTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.getPlayerId().equals(this.controllerId)
                && game.getOpponents(this.controllerId).contains(game.getActivePlayerId())) {
            return true;

        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell during an opponent's turn, " + super.getRule();
    }
}
