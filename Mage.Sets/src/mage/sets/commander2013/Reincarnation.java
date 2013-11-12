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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Reincarnation extends CardImpl<Reincarnation> {

    public Reincarnation(UUID ownerId) {
        super(ownerId, 166, "Reincarnation", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");
        this.expansionSetCode = "C13";

        this.color.setGreen(true);

        // Choose target creature. When that creature dies this turn, return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner.
        this.getSpellAbility().addEffect(new ReincarnationEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    }

    public Reincarnation(final Reincarnation card) {
        super(card);
    }

    @Override
    public Reincarnation copy() {
        return new Reincarnation(this);
    }
}

class ReincarnationEffect extends OneShotEffect<ReincarnationEffect> {

    public ReincarnationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature. When that creature dies this turn, return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner";
    }

    public ReincarnationEffect(final ReincarnationEffect effect) {
        super(effect);
    }

    @Override
    public ReincarnationEffect copy() {
        return new ReincarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new ReincarnationDelayedTriggeredAbility(targetPointer.getFirst(game, source));
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}

class ReincarnationDelayedTriggeredAbility extends DelayedTriggeredAbility<ReincarnationDelayedTriggeredAbility> {

    private UUID target;

    public ReincarnationDelayedTriggeredAbility(UUID target) {
        super(new ReincarnationDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public ReincarnationDelayedTriggeredAbility(ReincarnationDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ReincarnationDelayedTriggeredAbility copy() {
        return new ReincarnationDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner.";
    }
}

class ReincarnationDelayedEffect extends OneShotEffect<ReincarnationDelayedEffect> {

    private UUID target;

    public ReincarnationDelayedEffect(UUID target) {
        super(Outcome.Detriment);
        this.target = target;
        this.staticText = "return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner";
    }

    public ReincarnationDelayedEffect(final ReincarnationDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public ReincarnationDelayedEffect copy() {
        return new ReincarnationDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (permanent != null && controller != null) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player != null) {
                FilterCreatureCard filter = new FilterCreatureCard(new StringBuilder("a creature card from ").append(player.getName()).append("'s graveyard").toString());
                filter.add(new OwnerIdPredicate(player.getId()));
                Target targetCreature = new TargetCardInGraveyard(filter);
                targetCreature.setRequired(true);
                if (targetCreature.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseTarget(outcome, targetCreature, source, game)) {
                    Card card = game.getCard(targetCreature.getFirstTarget());
                    if (card != null && game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD)) {
                        return card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), player.getId());
                    }
                }
                return true;
            }
        }
        return false;
    }
}
