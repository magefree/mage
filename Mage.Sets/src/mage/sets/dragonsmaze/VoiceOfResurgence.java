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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;

/**
 *
 * @author jeffwadsworth
 */
public class VoiceOfResurgence extends CardImpl<VoiceOfResurgence> {

    public VoiceOfResurgence(UUID ownerId) {
        super(ownerId, 114, "Voice of Resurgence", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Elemental");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, put a green and white Elemental creature token onto the battlefield with "This creature's power and toughness are each equal to the number of creatures you control."
        this.addAbility(new VoiceOfResurgenceTriggeredAbility());

    }

    public VoiceOfResurgence(final VoiceOfResurgence card) {
        super(card);
    }

    @Override
    public VoiceOfResurgence copy() {
        return new VoiceOfResurgence(this);
    }
}

class VoiceOfResurgenceTriggeredAbility extends TriggeredAbilityImpl<VoiceOfResurgenceTriggeredAbility> {

    public VoiceOfResurgenceTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new VoiceOfResurgenceToken()), false);
    }

    public VoiceOfResurgenceTriggeredAbility(final VoiceOfResurgenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // Opponent casts spell during your turn
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && game.getOpponents(super.getControllerId()).contains(spell.getControllerId())
                    && game.getActivePlayerId().equals(super.getControllerId())) {
                return true;
            }
        }
        // Voice Of Resurgence Dies
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (super.getSourceId().equals(event.getTargetId())) {
                MageObject before = game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                Constants.Zone after = game.getState().getZone(event.getTargetId());
                return before != null && after != null && Constants.Zone.GRAVEYARD.match(after);
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, put a green and white Elemental creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of creatures you control.";
    }

    @Override
    public VoiceOfResurgenceTriggeredAbility copy() {
        return new VoiceOfResurgenceTriggeredAbility(this);
    }
}

class VoiceOfResurgenceToken extends Token {

    public VoiceOfResurgenceToken() {
        super("Elemental", "X/X green and white Elemental creature with with \"This creature's power and toughness are each equal to the number of creatures you control.");
        cardType.add(Constants.CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add("Elemental");
        power = new MageInt(0);
        toughness = new MageInt(0);
        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(new CardTypePredicate(CardType.CREATURE));
        DynamicValue creaturesControlled = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(creaturesControlled, Constants.Duration.EndOfGame)));
    }
}
