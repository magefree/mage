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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author emerald000
 */
public class SkysovereignConsulFlagship extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public SkysovereignConsulFlagship(UUID ownerId) {
        super(ownerId, 234, "Skysovereign, Consul Flagship", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "KLD";
        this.supertype.add("Legendary");
        this.subtype.add("Vehicle");
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skysovereign, Consul Flagship enters the battlefield or attacks, it deals 3 damage to target creature or planeswalker an opponent controls.
        Ability ability = new SkysovereignConsulFlagshipTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlaneswalker(1, 1, filter, false));
        this.addAbility(ability);

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    public SkysovereignConsulFlagship(final SkysovereignConsulFlagship card) {
        super(card);
    }

    @Override
    public SkysovereignConsulFlagship copy() {
        return new SkysovereignConsulFlagship(this);
    }
}

class SkysovereignConsulFlagshipTriggeredAbility extends TriggeredAbilityImpl {

    SkysovereignConsulFlagshipTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), false);
    }

    SkysovereignConsulFlagshipTriggeredAbility(final SkysovereignConsulFlagshipTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkysovereignConsulFlagshipTriggeredAbility copy() {
        return new SkysovereignConsulFlagshipTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED || event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or attacks, it deals 3 damage to target creature or planeswalker an opponent controls.";
    }
}
