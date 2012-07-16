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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public class SoulSeizer extends CardImpl<SoulSeizer> {

    public SoulSeizer(UUID ownerId) {
        super(ownerId, 50, "Soul Seizer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Spirit");

        this.canTransform = true;
        this.secondSideCard = new GhastlyHaunting(ownerId);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // When Soul Seizer deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls.
        this.addAbility(new TransformAbility());
        this.addAbility(new SoulSeizerTriggeredAbility());
    }

    public SoulSeizer(final SoulSeizer card) {
        super(card);
    }

    @Override
    public SoulSeizer copy() {
        return new SoulSeizer(this);
    }
}

class SoulSeizerTriggeredAbility extends TriggeredAbilityImpl<SoulSeizerTriggeredAbility> {

    public SoulSeizerTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SoulSeizerEffect(), true);
    }

    public SoulSeizerTriggeredAbility(SoulSeizerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoulSeizerTriggeredAbility copy() {
        return new SoulSeizerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
                Player opponent = game.getPlayer(event.getPlayerId());
                if (opponent != null) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getName() + " controls");
                    filter.add(new ControllerIdPredicate(opponent.getId()));

                    this.getTargets().clear();
                    this.addTarget(new TargetCreaturePermanent(filter));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls";
    }
}

class SoulSeizerEffect extends OneShotEffect<SoulSeizerEffect> {

    public SoulSeizerEffect() {
        super(Outcome.GainControl);
    }

    public SoulSeizerEffect(final SoulSeizerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.canTransform()) {
            if (permanent.transform(game)) {
                Permanent attachTo = game.getPermanent(targetPointer.getFirst(game, source));
                if (attachTo != null) {
                    return attachTo.addAttachment(source.getSourceId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public SoulSeizerEffect copy() {
        return new SoulSeizerEffect(this);
    }

}