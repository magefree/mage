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
package mage.sets.zendikar;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Viserion
 */
public class ValakutTheMoltenPinnacle extends CardImpl {

    static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Mountain"));
    }

    public ValakutTheMoltenPinnacle(UUID ownerId) {
        super(ownerId, 228, "Valakut, the Molten Pinnacle", Rarity.RARE, new CardType[]{CardType.LAND}, null);
        this.expansionSetCode = "ZEN";

        // Valakut, the Molten Pinnacle enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have Valakut, the Molten Pinnacle deal 3 damage to target creature or player.
        this.addAbility(new ValakutTheMoltenPinnacleTriggeredAbility());
        // {T}: Add {R} to your mana pool.
        this.addAbility(new RedManaAbility());

    }

    public ValakutTheMoltenPinnacle(final ValakutTheMoltenPinnacle card) {
        super(card);
    }

    @Override
    public ValakutTheMoltenPinnacle copy() {
        return new ValakutTheMoltenPinnacle(this);
    }
}

class ValakutTheMoltenPinnacleTriggeredAbility extends TriggeredAbilityImpl {

    ValakutTheMoltenPinnacleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), true);
        this.addTarget(new TargetCreatureOrPlayer());
    }

    ValakutTheMoltenPinnacleTriggeredAbility(ValakutTheMoltenPinnacleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().count(ValakutTheMoltenPinnacle.filter, getSourceId(), getControllerId(), game) > 5;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.getCardType().contains(CardType.LAND) && permanent.getControllerId().equals(this.getControllerId())) {
            if (permanent.hasSubtype("Mountain", game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ValakutTheMoltenPinnacleTriggeredAbility copy() {
        return new ValakutTheMoltenPinnacleTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have {this} deal 3 damage to target creature or player.";
    }
}
