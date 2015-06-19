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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class SaffiEriksdotter extends CardImpl {

    public SaffiEriksdotter(UUID ownerId) {
        super(ownerId, 245, "Saffi Eriksdotter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.expansionSetCode = "TSP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Scout");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Saffi Eriksdotter: When target creature is put into your graveyard from the battlefield this turn, return that card to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SaffiEriksdotterEffect(), new SacrificeSourceCost());
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent());
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public SaffiEriksdotter(final SaffiEriksdotter card) {
        super(card);
    }

    @Override
    public SaffiEriksdotter copy() {
        return new SaffiEriksdotter(this);
    }
}

class SaffiEriksdotterEffect extends OneShotEffect {

    public SaffiEriksdotterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When target creature is put into your graveyard from the battlefield this turn, return that card to the battlefield";
    }

    public SaffiEriksdotterEffect(final SaffiEriksdotterEffect effect) {
        super(effect);
    }

    @Override
    public SaffiEriksdotterEffect copy() {
        return new SaffiEriksdotterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SaffiEriksdotterDelayedTriggeredAbility(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        delayedAbility.setSourceObject(source.getSourceObject(game), game);
        game.addDelayedTriggeredAbility(delayedAbility);
        return false;
    }
}


class SaffiEriksdotterDelayedTriggeredAbility extends DelayedTriggeredAbility {

    protected FixedTarget fixedTarget;

    public SaffiEriksdotterDelayedTriggeredAbility(FixedTarget fixedTarget) {
        super(new ReturnToBattlefieldUnderYourControlTargetEffect(), Duration.EndOfTurn);
        this.getEffects().get(0).setTargetPointer(fixedTarget);
        this.fixedTarget = fixedTarget;
    }

    public SaffiEriksdotterDelayedTriggeredAbility(final SaffiEriksdotterDelayedTriggeredAbility ability) {
        super(ability);
        this.fixedTarget = ability.fixedTarget;
    }

    @Override
    public SaffiEriksdotterDelayedTriggeredAbility copy() {
        return new SaffiEriksdotterDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            if (fixedTarget.getFirst(game, this).equals(event.getTargetId())) {
                if (this.getControllerId().equals(event.getPlayerId())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When target creature is put into your graveyard from the battlefield this turn, " + super.getRule();
    }
}


