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
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public class SpectralForce extends CardImpl {

    public SpectralForce(UUID ownerId) {
        super(ownerId, 217, "Spectral Force", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Elemental");
        this.subtype.add("Spirit");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Whenever Spectral Force attacks, if defending player controls no black permanents, it doesn't untap during your next untap step.
        this.addAbility(new SpectralForceTriggeredAbility());
    }

    public SpectralForce(final SpectralForce card) {
        super(card);
    }

    @Override
    public SpectralForce copy() {
        return new SpectralForce(this);
    }
}

class SpectralForceTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("black permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SpectralForceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepSourceEffect());
    }

    public SpectralForceTriggeredAbility(final SpectralForceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpectralForceTriggeredAbility copy() {
        return new SpectralForceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
        return defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) < 1;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, if defending player controls no black permanents, it doesn't untap during your next untap step.";
    }
}