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
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class PutCardIntoGraveFromAnywhereAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCard filter;
    private final String ruleText;
    private final SetTargetPointer setTargetPointer;

    public PutCardIntoGraveFromAnywhereAllTriggeredAbility(Effect effect, boolean optional, TargetController targetController) {
        this(effect, optional, new FilterCard("a card"), targetController);
    }

    public PutCardIntoGraveFromAnywhereAllTriggeredAbility(Effect effect, boolean optional, FilterCard filter, TargetController targetController) {
        this(effect, optional, filter, targetController, SetTargetPointer.NONE);
    }

    public PutCardIntoGraveFromAnywhereAllTriggeredAbility(Effect effect, boolean optional, FilterCard filter, TargetController targetController, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, filter, targetController, setTargetPointer);
    }

    public PutCardIntoGraveFromAnywhereAllTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterCard filter, TargetController targetController, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter.copy();
        this.setTargetPointer = setTargetPointer;
        this.filter.add(new OwnerPredicate(targetController));
        StringBuilder sb = new StringBuilder("Whenever ");
        sb.append(filter.getMessage());
        sb.append(" is put into ");
        switch (targetController) {
            case OPPONENT:
                sb.append("an opponent's");
                break;
            case YOU:
                sb.append("your");
                break;
            default:
                sb.append("a");
        }
        sb.append(" graveyard, ");
        ruleText = sb.toString();

    }

    public PutCardIntoGraveFromAnywhereAllTriggeredAbility(final PutCardIntoGraveFromAnywhereAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.ruleText = ability.ruleText;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public PutCardIntoGraveFromAnywhereAllTriggeredAbility copy() {
        return new PutCardIntoGraveFromAnywhereAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && filter.match(card, getSourceId(), getControllerId(), game)) {
                switch (setTargetPointer) {
                    case CARD:
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                        }
                        break;
                    case PLAYER:
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(card.getOwnerId(), 0));
                        }
                        break;

                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return ruleText + super.getRule();
    }
}
