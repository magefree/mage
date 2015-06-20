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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author LevelX2
 */
public class Detritivore extends CardImpl {

    public Detritivore(UUID ownerId) {
        super(ownerId, 96, "Detritivore", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Lhurgoyf");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Detritivore's power and toughness are each equal to the number of nonbasic land cards in your opponents' graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new NonBasicLandsInOpponentsGraveyards(), Duration.EndOfGame)));

        // Suspend X-{X}{3}{R}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl("{3}{R}"), this, true));

        // Whenever a time counter is removed from Detritivore while it's exiled, destroy target nonbasic land.
        this.addAbility(new DetritivoreTriggeredAbility());

    }

    public Detritivore(final Detritivore card) {
        super(card);
    }

    @Override
    public Detritivore copy() {
        return new Detritivore(this);
    }
}

class DetritivoreTriggeredAbility extends TriggeredAbilityImpl {

    public DetritivoreTriggeredAbility() {
        super(Zone.EXILED, new DestroyTargetEffect(), false);
        this.addTarget(new TargetNonBasicLandPermanent());
    }

    public DetritivoreTriggeredAbility(final DetritivoreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DetritivoreTriggeredAbility copy() {
        return new DetritivoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever a time counter is removed from {this} while it's exiled, " + super.getRule();
    }

}

class NonBasicLandsInOpponentsGraveyards implements DynamicValue {

    private static final FilterCard filter = new FilterCard("nonbasic land card");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.add(Predicates.not(new SupertypePredicate("Basic")));
    }


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (UUID playerUUID : controller.getInRange()) {
                if (controller.hasOpponent(playerUUID, game)) {
                    Player player = game.getPlayer(playerUUID);
                    if (player != null) {
                        amount += player.getGraveyard().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
                    }
                }
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of nonbasic land cards in your opponents' graveyards";
    }
}
