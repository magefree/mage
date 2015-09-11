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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class AnafenzaTheForemost extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target tapped creature you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new TappedPredicate());
    }

    public AnafenzaTheForemost(UUID ownerId) {
        super(ownerId, 163, "Anafenza, the Foremost", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");
        this.expansionSetCode = "KTK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);

        // If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnafenzaTheForemostEffect()));
    }

    public AnafenzaTheForemost(final AnafenzaTheForemost card) {
        super(card);
    }

    @Override
    public AnafenzaTheForemost copy() {
        return new AnafenzaTheForemost(this);
    }
}

class AnafenzaTheForemostEffect extends ReplacementEffectImpl {

    public AnafenzaTheForemostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature card would be put into an opponent's graveyard from anywhere, exile it instead";
    }

    public AnafenzaTheForemostEffect(final AnafenzaTheForemostEffect effect) {
        super(effect);
    }

    @Override
    public AnafenzaTheForemostEffect copy() {
        return new AnafenzaTheForemostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone().equals(Zone.BATTLEFIELD)) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    return controller.moveCardToExileWithInfo(permanent, null, null, source.getSourceId(), game, Zone.BATTLEFIELD, true);
                }
            } else {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    return controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.ZONE_CHANGE);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && game.getOpponents(source.getControllerId()).contains(card.getOwnerId())) { // Anafenza only cares about cards
                if (zEvent.getTarget() != null) { // if it comes from permanent, check if it was a creature on the battlefield
                    if (zEvent.getTarget().getCardType().contains(CardType.CREATURE)) {
                        return true;
                    }
                } else if (card.getCardType().contains(CardType.CREATURE)) {
                    return true;
                }
            }
        }
        return false;
    }

}
