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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class NirkanaRevenant extends CardImpl {

    public NirkanaRevenant(UUID ownerId) {
        super(ownerId, 120, "Nirkana Revenant", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Vampire");
        this.subtype.add("Shade");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you tap a Swamp for mana, add {B} to your mana pool.
        this.addAbility(new NirkanaRevenantTriggeredAbility());

        // {B}: Nirkana Revenant gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl("{B}")));
    }

    public NirkanaRevenant(final NirkanaRevenant card) {
        super(card);
    }

    @Override
    public NirkanaRevenant copy() {
        return new NirkanaRevenant(this);
    }
}

class NirkanaRevenantTriggeredAbility extends TriggeredManaAbility {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Swamp");
    static {
            filter.add(new SubtypePredicate("Swamp"));
    }

    public NirkanaRevenantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana), false);
        this.usesStack = false;
    }

    public NirkanaRevenantTriggeredAbility(NirkanaRevenantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land != null && filter.match(land, this.getSourceId(), this.getControllerId(), game);
    }

    @Override
    public NirkanaRevenantTriggeredAbility copy() {
        return new NirkanaRevenantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, add {B} to your mana pool.";
    }
}
