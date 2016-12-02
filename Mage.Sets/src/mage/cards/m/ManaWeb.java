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
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author spjspj
 */
public class ManaWeb extends CardImpl {

    public ManaWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever a land an opponent controls is tapped for mana, tap all lands that player controls that could produce any type of mana that land could produce.
        this.addAbility(new ManaWebTriggeredAbility());
    }

    public ManaWeb(final ManaWeb card) {
        super(card);
    }

    @Override
    public ManaWeb copy() {
        return new ManaWeb(this);
    }
}

class ManaWebTriggeredAbility extends TriggeredAbilityImpl {

    public ManaWebTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ManaWebeffect(), false);
    }

    private static final String staticText = "Whenever a land an opponent controls is tapped for mana, tap all lands that player controls that could produce any type of mana that land could produce.";

    public ManaWebTriggeredAbility(ManaWebTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());

            if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public ManaWebTriggeredAbility copy() {
        return new ManaWebTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}

class ManaWebeffect extends OneShotEffect {

    private final static FilterLandPermanent filter = new FilterLandPermanent("an opponent taps a land");

    public ManaWebeffect() {
        super(Outcome.Tap);
        staticText = "Whenever a land an opponent controls is tapped for mana, tap all lands that player controls that could produce any type of mana that land could produce.";
    }

    public ManaWebeffect(final ManaWebeffect effect) {
        super(effect);
    }

    @Override
    public ManaWebeffect copy() {
        return new ManaWebeffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;

        if (game != null && source != null) {
            permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        }

        if (permanent != null && game != null) {
            Mana mana = new Mana();

            for (ActivatedManaAbilityImpl ability : permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                for (Mana netMana : ability.getNetMana(game)) {
                    mana.add(netMana);
                }
            }

            boolean tappedLands = false;
            for (Permanent opponentPermanent : game.getBattlefield().getActivePermanents(filter, permanent.getControllerId(), game)) {
                if (opponentPermanent.getControllerId() == permanent.getControllerId()) {
                    Mana opponentLandMana = new Mana();

                    for (ActivatedManaAbilityImpl ability : opponentPermanent.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, game)) {
                        for (Mana netMana : ability.getNetMana(game)) {
                            opponentLandMana.add(netMana);
                        }
                    }

                    if (mana.containsAny(opponentLandMana)) {
                        tappedLands = opponentPermanent.tap(game) || tappedLands;
                    }
                }
            }
            return tappedLands;
        }
        return false;
    }
}
