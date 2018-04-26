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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GoldenGuardian extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public GoldenGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.transformable = true;
        this.secondSideCardClazz = GoldForgeGarrison.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}: Golden Guardian fights another target creature you control. When Golden Guardian dies this turn, return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new GoldenGuardianEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new GoldenGuardianDelayedTriggeredAbility(), false));
        this.addAbility(ability);

    }

    public GoldenGuardian(final GoldenGuardian card) {
        super(card);
    }

    @Override
    public GoldenGuardian copy() {
        return new GoldenGuardian(this);
    }
}

class GoldenGuardianEffect extends OneShotEffect {

    public GoldenGuardianEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} fights another target creature you control";
    }

    public GoldenGuardianEffect(final GoldenGuardianEffect effect) {
        super(effect);
    }

    @Override
    public GoldenGuardianEffect copy() {
        return new GoldenGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getSourceId());
        Permanent creature2 = game.getPermanent(source.getFirstTarget());
        // 20110930 - 701.10
        if (creature1 != null && creature2 != null) {
            if (creature1.isCreature() && creature2.isCreature()) {
                return creature1.fight(creature2, source, game);
            }
        }
        return false;
    }
}

class GoldenGuardianDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public GoldenGuardianDelayedTriggeredAbility() {
        super(new GoldenGuardianReturnTransformedEffect(), Duration.EndOfTurn);
    }

    public GoldenGuardianDelayedTriggeredAbility(final GoldenGuardianDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoldenGuardianDelayedTriggeredAbility copy() {
        return new GoldenGuardianDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((ZoneChangeEvent) event).isDiesEvent() && event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "When {this} dies this turn, " + super.getRule();
    }
}

class GoldenGuardianReturnTransformedEffect extends OneShotEffect {

    public GoldenGuardianReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    public GoldenGuardianReturnTransformedEffect(final GoldenGuardianReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public GoldenGuardianReturnTransformedEffect copy() {
        return new GoldenGuardianReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
                Card card = game.getCard(source.getSourceId());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }

}
