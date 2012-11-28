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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author Plopman
 */
public class EnergyField extends CardImpl<EnergyField> {

    public EnergyField(UUID ownerId) {
        super(ownerId, 73, "Energy Field", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "USG";

        this.color.setBlue(true);

        // Prevent all damage that would be dealt to you by sources you don't control.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EnergyFieldEffect()));
        // When a card is put into your graveyard from anywhere, sacrifice Energy Field.
        this.addAbility(new PutIntoYourGraveyardTriggeredAbility());
    }

    public EnergyField(final EnergyField card) {
        super(card);
    }

    @Override
    public EnergyField copy() {
        return new EnergyField(this);
    }
}

class EnergyFieldEffect extends PreventionEffectImpl<EnergyFieldEffect> {

    public EnergyFieldEffect() {
        super(Constants.Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to you by sources you don't control";
    }

    public EnergyFieldEffect(EnergyFieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.informPlayers("Damage has been prevented: " + damage);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)) {
            if (event.getTargetId().equals(source.getControllerId()) && game.getControllerId(event.getSourceId()) != source.getControllerId()){
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public EnergyFieldEffect copy() {
        return new EnergyFieldEffect(this);
    }
}

class PutIntoYourGraveyardTriggeredAbility extends TriggeredAbilityImpl<PutIntoYourGraveyardTriggeredAbility> {



    public PutIntoYourGraveyardTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
    }

    public PutIntoYourGraveyardTriggeredAbility(PutIntoYourGraveyardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutIntoYourGraveyardTriggeredAbility copy() {
        return new PutIntoYourGraveyardTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
                if(game.getControllerId(event.getTargetId()) != this.getControllerId()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a card is put into your graveyard from anywhere, " + super.getRule();
    }
}