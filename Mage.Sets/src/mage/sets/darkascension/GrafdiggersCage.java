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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward
 */
public class GrafdiggersCage extends CardImpl<GrafdiggersCage> {

    public GrafdiggersCage(UUID ownerId) {
        super(ownerId, 149, "Grafdigger's Cage", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "DKA";

        // Creature cards can't enter the battlefield from graveyards or libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrafdiggersCageEffect()));
        
        // Players can't cast cards in graveyards or libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrafdiggersCageEffect2()));
    }

    public GrafdiggersCage(final GrafdiggersCage card) {
        super(card);
    }

    @Override
    public GrafdiggersCage copy() {
        return new GrafdiggersCage(this);
    }
}

class GrafdiggersCageEffect extends ReplacementEffectImpl<GrafdiggersCageEffect> {

	public GrafdiggersCageEffect() {
		super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
		staticText = "Creature cards can't enter the battlefield from graveyards or libraries";
	}

	public GrafdiggersCageEffect(final GrafdiggersCageEffect effect) {
		super(effect);
	}

	@Override
	public GrafdiggersCageEffect copy() {
		return new GrafdiggersCageEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof ZoneChangeEvent) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD && (zEvent.getFromZone() == Zone.GRAVEYARD || zEvent.getFromZone() == Zone.LIBRARY)) {
                Card card = game.getCard(zEvent.getTargetId());
                if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                    return true;
                }
            }
        }
		return false;
	}

}

class GrafdiggersCageEffect2 extends ReplacementEffectImpl<GrafdiggersCageEffect2> {

	public GrafdiggersCageEffect2() {
		super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
		staticText = "Players can't cast cards in graveyards or libraries";
	}

	public GrafdiggersCageEffect2(final GrafdiggersCageEffect2 effect) {
		super(effect);
	}

	@Override
	public GrafdiggersCageEffect2 copy() {
		return new GrafdiggersCageEffect2(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Card card = game.getCard(event.getSourceId());
            if (card != null) {
                Zone zone = game.getState().getZone(card.getId());
                if (zone != null && (zone == Constants.Zone.GRAVEYARD || zone == Constants.Zone.LIBRARY)) {
                    return true;
                }
            }
		}
		return false;
	}

}
