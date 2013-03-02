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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class InvaderParasite extends CardImpl<InvaderParasite> {

    public InvaderParasite(UUID ownerId) {
        super(ownerId, 87, "Invader Parasite", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Insect");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        Ability ability = new EntersBattlefieldTriggeredAbility(new InvaderParasiteImprintEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
        this.addAbility(new InvaderParasiteTriggeredAbility());
    }

    public InvaderParasite(final InvaderParasite card) {
        super(card);
    }

    @Override
    public InvaderParasite copy() {
        return new InvaderParasite(this);
    }
}

class InvaderParasiteImprintEffect extends OneShotEffect<InvaderParasiteImprintEffect> {
    InvaderParasiteImprintEffect() {
        super(Constants.Outcome.Exile);
        staticText = "exile target land";
    }

    InvaderParasiteImprintEffect(final InvaderParasiteImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (sourcePermanent != null && targetPermanent != null) {
            targetPermanent.moveToExile(getId(), "Invader Parasite (Imprint)", source.getSourceId(), game);
            sourcePermanent.imprint(targetPermanent.getId(), game);
        }
        return true;
    }

    @Override
    public InvaderParasiteImprintEffect copy() {
        return new InvaderParasiteImprintEffect(this);
    }
}

class InvaderParasiteTriggeredAbility extends TriggeredAbilityImpl<InvaderParasiteTriggeredAbility> {
    InvaderParasiteTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    InvaderParasiteTriggeredAbility(final InvaderParasiteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InvaderParasiteTriggeredAbility copy() {
        return new InvaderParasiteTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            Permanent p = game.getPermanent(event.getTargetId());
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (p != null && sourcePermanent != null) {
                if (sourcePermanent.getImprinted().size() > 0) {
                    Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
                    if (p.getName().equals(imprintedCard.getName())) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land with the same name as the exiled card enters the battlefield under an opponent's control, {this} deals 2 damage to that player";
    }
}
