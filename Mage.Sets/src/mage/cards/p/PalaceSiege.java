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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class PalaceSiege extends CardImpl {

    private final static String ruleTrigger1 = "&bull Khans &mdash; At the beginning of your upkeep, return target creature card from your graveyard to your hand.";
    private final static String ruleTrigger2 = "&bull Dragons &mdash; At the beginning of your upkeep, each opponent loses 2 life and you gain 2 life.";
    
    public PalaceSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // As Palace Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?","Khans", "Dragons"),null,
                "As {this} enters the battlefield, choose Khans or Dragons.",""));
        
        // * Khans - At the beginning of your upkeep, return target creature card from your graveyard to your hand.
        Ability ability1 = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1);
        ability1.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
        this.addAbility(ability1);
        
        // * Dragons - At the beginning of your upkeep, each opponent loses 2 life and you gain 2 life.
        Ability ability2 = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsEffect(2), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability2.addEffect(effect);
        this.addAbility(ability2);
        
    }

    public PalaceSiege(final PalaceSiege card) {
        super(card);
    }

    @Override
    public PalaceSiege copy() {
        return new PalaceSiege(this);
    }
}
