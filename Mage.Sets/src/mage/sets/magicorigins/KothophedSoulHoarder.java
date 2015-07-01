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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class KothophedSoulHoarder extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new OwnerPredicate(TargetController.NOT_YOU));
    }

    public KothophedSoulHoarder(UUID ownerId) {
        super(ownerId, 104, "Kothophed, Soul Hoarder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "ORI";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a permanent owned by another player is put into the graveyard from the battlefield, you draw one card and lose 1 life.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("you draw one card");
        Ability ability = new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD, effect, filter,
                "Whenever a permanent owned by another player is put into the graveyard from the battlefield, ", false);
        effect = new LoseLifeSourceControllerEffect(1);
        effect.setText("and lose 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public KothophedSoulHoarder(final KothophedSoulHoarder card) {
        super(card);
    }

    @Override
    public KothophedSoulHoarder copy() {
        return new KothophedSoulHoarder(this);
    }
}

class KothophedSoulHoarderTriggeredAbility extends TriggeredAbilityImpl {

    public KothophedSoulHoarderTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public KothophedSoulHoarderTriggeredAbility(final KothophedSoulHoarderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KothophedSoulHoarderTriggeredAbility copy() {
        return new KothophedSoulHoarderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone().equals(Zone.GRAVEYARD) && zEvent.getFromZone().equals(Zone.BATTLEFIELD)) {
            Card card = game.getCard(zEvent.getTargetId());
            Player controller = game.getPlayer(getControllerId());
            return card != null && controller != null && controller.hasOpponent(card.getOwnerId(), game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature leaves an opponent's graveyard, " + super.getRule();
    }
}
