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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth and LevelX2
 */
public class DuskmantleGuildmage extends CardImpl<DuskmantleGuildmage> {

    public DuskmantleGuildmage(UUID ownerId) {
        super(ownerId, 158, "Duskmantle Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}{B}: Whenever a card is put into an opponent's graveyard from anywhere this turn, that player loses 1 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new CardPutIntoOpponentGraveThisTurn()), new ManaCostsImpl("{1}{U}{B}")));

        // {2}{U}{B}: Target player puts the top two cards of his or her library into his or her graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(2), new ManaCostsImpl("{2}{U}{B}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public DuskmantleGuildmage(final DuskmantleGuildmage card) {
        super(card);
    }

    @Override
    public DuskmantleGuildmage copy() {
        return new DuskmantleGuildmage(this);
    }
}

class CardPutIntoOpponentGraveThisTurn extends DelayedTriggeredAbility<CardPutIntoOpponentGraveThisTurn> {

    public CardPutIntoOpponentGraveThisTurn() {
        super(new LoseLifeTargetEffect(1), Duration.EndOfTurn, false);
    }

    public CardPutIntoOpponentGraveThisTurn(final CardPutIntoOpponentGraveThisTurn ability) {
        super(ability);
    }

    @Override
    public CardPutIntoOpponentGraveThisTurn copy() {
        return new CardPutIntoOpponentGraveThisTurn(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            UUID cardId = event.getTargetId();
            Card card = game.getCard(cardId);
            if (zEvent.getToZone() == Zone.GRAVEYARD
                    && game.getOpponents(controllerId).contains(card.getOwnerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(card.getOwnerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a card is put into an opponent's graveyard from anywhere this turn, that player loses 1 life";
    }
}