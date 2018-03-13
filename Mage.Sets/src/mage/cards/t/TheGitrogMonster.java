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
package mage.cards.t;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class TheGitrogMonster extends CardImpl {

    public TheGitrogMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // At the beginning of your upkeep, sacrifice The Gitrog Monster unless you sacrifice a land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(
                new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent("a land"), true))), TargetController.YOU, false));

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));

        // Whenever one or more land cards are put into your graveyard from anywhere, draw a card.
        this.addAbility(new TheGitrogMonsterTriggeredAbility());
    }

    public TheGitrogMonster(final TheGitrogMonster card) {
        super(card);
    }

    @Override
    public TheGitrogMonster copy() {
        return new TheGitrogMonster(this);
    }
}

class TheGitrogMonsterTriggeredAbility extends TriggeredAbilityImpl {

    public TheGitrogMonsterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public TheGitrogMonsterTriggeredAbility(final TheGitrogMonsterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null && Zone.GRAVEYARD == zEvent.getToZone() && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {                    
                    UUID cardOwnerId = card.getOwnerId();
                    Set<CardType> cardType = card.getCardType();
                    if (cardOwnerId != null
                            && card.getOwnerId().equals(getControllerId())
                            && cardType != null
                            && card.isLand()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TheGitrogMonsterTriggeredAbility copy() {
        return new TheGitrogMonsterTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard from anywhere, draw a card.";
    }
}
