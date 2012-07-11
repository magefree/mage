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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX
 */
public class HorobiDeathsWail extends CardImpl<HorobiDeathsWail> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Demon");
    static {
        filter.add(new SubtypePredicate("Demon"));
    }

    public HorobiDeathsWail(UUID ownerId) {
        super(ownerId, 117, "Horobi Death's Wail", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a creature becomes the target of a spell or ability, destroy that creature.
        this.addAbility(new HorobiDeathsWailAbility(new DestroyTargetEffect()));
    }

    public HorobiDeathsWail(final HorobiDeathsWail card) {
        super(card);
    }

    @Override
    public HorobiDeathsWail copy() {
        return new HorobiDeathsWail(this);
    }    

}

class HorobiDeathsWailAbility extends TriggeredAbilityImpl<HorobiDeathsWailAbility> {

    public HorobiDeathsWailAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public HorobiDeathsWailAbility(final HorobiDeathsWailAbility ability) {
        super(ability);
    }

    @Override
    public HorobiDeathsWailAbility copy() {
        return new HorobiDeathsWailAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.TARGETED) {
            Permanent creature = game.getPermanent(event.getTargetId());
            if (creature != null && creature.getCardType().contains(CardType.CREATURE)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a creature becomes the target of a spell or ability, destroy that creature.";
    }
}

