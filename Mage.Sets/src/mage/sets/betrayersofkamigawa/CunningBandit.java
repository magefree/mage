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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CunningBandit extends CardImpl<CunningBandit> {

    private static final FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    public CunningBandit(UUID ownerId) {
        super(ownerId, 99, "Cunning Bandit", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Azamuki, Treachery Incarnate";

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Cunning Bandit.
         this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), filter, true));

        // At the beginning of the end step, if there are two or more ki counters on Cunning Bandit, you may flip it.
        this.addAbility(new ConditionalTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect()),
                new HasCounterCondition(CounterType.KI, 2, Integer.MAX_VALUE),
                "At the beginning of the end step, if there are two or more ki counters on {this}, you may flip it.", true));

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new AzamukiTreacheryIncarnate()), FlippedCondition.getInstance(), ""));
        ability.setRuleVisible(false);
        this.addAbility(ability);
    }

    public CunningBandit(final CunningBandit card) {
        super(card);
    }

    @Override
    public CunningBandit copy() {
        return new CunningBandit(this);
    }
}

class AzamukiTreacheryIncarnate extends Token {

    AzamukiTreacheryIncarnate() {
        super("Azamuki, Treachery Incarnate", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Spirit");
        power = new MageInt(5);
        toughness = new MageInt(2);

        // Remove a ki counter from Azamuki, Treachery Incarnate: Gain control of target creature until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainControlTargetEffect(Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);
    }
}
