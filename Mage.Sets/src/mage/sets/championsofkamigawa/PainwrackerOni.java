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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX
 */
public class PainwrackerOni extends CardImpl<PainwrackerOni> {

    public PainwrackerOni (UUID ownerId) {
        super(ownerId, 136, "Painwracker Oni", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Demon");
        this.subtype.add("Spirit");
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Fear (This creature can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(FearAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a creature if you don't control an Ogre.
        Ability ability = new PainwrackerOniTriggeredAbility1(new SacrificeTargetEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public PainwrackerOni (final PainwrackerOni card) {
        super(card);
    }

    @Override
    public PainwrackerOni copy() {
        return new PainwrackerOni(this);
    }

}

class PainwrackerOniTriggeredAbility1 extends TriggeredAbilityImpl<PainwrackerOniTriggeredAbility1> {

    private final static FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new SubtypePredicate("Ogre"));
    }

    public PainwrackerOniTriggeredAbility1(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public PainwrackerOniTriggeredAbility1(final PainwrackerOniTriggeredAbility1 ability) {
        super(ability);
    }

    @Override
    public PainwrackerOniTriggeredAbility1 copy() {
        return new PainwrackerOniTriggeredAbility1(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) < 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, sacrifice a creature if you don't control an Ogre.";
    }
}