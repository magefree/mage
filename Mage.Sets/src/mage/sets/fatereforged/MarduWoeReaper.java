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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public class MarduWoeReaper extends CardImpl {

    public MarduWoeReaper(UUID ownerId) {
        super(ownerId, 18, "Mardu Woe-Reaper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Mardu Woe-Reaper or another Warrior enters the battlefield under your control, you may exile target creature card from a graveyard. If you do, you gain 1 life.
        Ability ability = new MarduWoeReaperTriggeredAbility();
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    public MarduWoeReaper(final MarduWoeReaper card) {
        super(card);
    }

    @Override
    public MarduWoeReaper copy() {
        return new MarduWoeReaper(this);
    }
}

class MarduWoeReaperTriggeredAbility extends TriggeredAbilityImpl {

    MarduWoeReaperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MarduWoeReaperEffect(), true);
    }

    MarduWoeReaperTriggeredAbility(final MarduWoeReaperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarduWoeReaperTriggeredAbility copy() {
        return new MarduWoeReaperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && (permanent.getId() == this.getSourceId() || permanent.hasSubtype("Warrior"))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Warrior enters the battlefield under your control, " + super.getRule();
    }
}

class MarduWoeReaperEffect extends OneShotEffect {
    
    MarduWoeReaperEffect() {
        super(Outcome.GainLife);
        this.staticText = "you may exile target creature card from a graveyard. If you do, you gain 1 life";
    }
    
    MarduWoeReaperEffect(final MarduWoeReaperEffect effect) {
        super(effect);
    }
    
    @Override
    public MarduWoeReaperEffect copy() {
        return new MarduWoeReaperEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (player != null && card != null) {
            if (player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true)) {
                player.gainLife(1, game);
            }
            return true;
        }
        return false;
    }
}
