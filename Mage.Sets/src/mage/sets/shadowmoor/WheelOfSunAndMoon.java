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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class WheelOfSunAndMoon extends CardImpl {

    public WheelOfSunAndMoon(UUID ownerId) {
        super(ownerId, 243, "Wheel of Sun and Moon", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{G/W}{G/W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Aura");

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // If a card would be put into enchanted player's graveyard from anywhere, instead that card is revealed and put on the bottom of that player's library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WheelOfSunAndMoonEffect()));
    }

    public WheelOfSunAndMoon(final WheelOfSunAndMoon card) {
        super(card);
    }

    @Override
    public WheelOfSunAndMoon copy() {
        return new WheelOfSunAndMoon(this);
    }
}

class WheelOfSunAndMoonEffect extends ReplacementEffectImpl {

    public WheelOfSunAndMoonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If a card would be put into enchanted player's graveyard from anywhere, instead that card is revealed and put on the bottom of that player's library";
    }

    public WheelOfSunAndMoonEffect(final WheelOfSunAndMoonEffect effect) {
        super(effect);
    }

    @Override
    public WheelOfSunAndMoonEffect copy() {
        return new WheelOfSunAndMoonEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone().equals(Zone.GRAVEYARD)) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Permanent enchantment = game.getPermanent(source.getSourceId());
                if (enchantment != null && enchantment.getAttachedTo() != null
                        && card.getOwnerId().equals(enchantment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                return true;
            }
        }
        return false;
    }

}
