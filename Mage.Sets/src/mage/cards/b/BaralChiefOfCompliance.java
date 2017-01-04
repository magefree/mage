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
package mage.cards.b;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.watchers.Watcher;

/**
 *
 * @author fireshoes
 */
public class BaralChiefOfCompliance extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and sorcery spells");
    static {
        filter.add(Predicates.or(
            new CardTypePredicate(CardType.INSTANT),
            new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public BaralChiefOfCompliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever a spell or ability you control counters a spell, you may draw a card. If you do, discard a card.
        this.addAbility(new BaralChiefOfComplianceTriggeredAbility(), new CastedSpellsWithSpellTarget());
    }

    public BaralChiefOfCompliance(final BaralChiefOfCompliance card) {
        super(card);
    }

    @Override
    public BaralChiefOfCompliance copy() {
        return new BaralChiefOfCompliance(this);
    }
}

class BaralChiefOfComplianceTriggeredAbility extends TriggeredAbilityImpl {

    public BaralChiefOfComplianceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), true);
    }

    public BaralChiefOfComplianceTriggeredAbility(final BaralChiefOfComplianceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BaralChiefOfComplianceTriggeredAbility copy() {
        return new BaralChiefOfComplianceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTERED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CastedSpellsWithSpellTarget watcher = (CastedSpellsWithSpellTarget) game.getState().getWatchers().get("CastedSpellsWithSpellTarget");
        UUID controllerIdCounter = watcher.getControllerSpell(event.getSourceId(), event.getTargetId());
        return controllerIdCounter != null && controllerIdCounter.equals(controllerId);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a spell or ability you control counters a spell, ").append(super.getRule()).toString();
    }
}

class CastedSpellsWithSpellTarget extends Watcher {

    private final Map<String, UUID> casted = new HashMap<>();

    public CastedSpellsWithSpellTarget() {
        super("CastedSpellsWithSpellTarget", WatcherScope.GAME);
    }

    public CastedSpellsWithSpellTarget(final CastedSpellsWithSpellTarget watcher) {
        super(watcher);
        for (Map.Entry<String, UUID> entry: watcher.casted.entrySet()) {
            casted.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            if (stackObject == null) {
                stackObject = (StackObject) game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
            }
            if (stackObject != null && stackObject.getStackAbility() != null) {
                for (Target target: stackObject.getStackAbility().getTargets()) {
                    if (target instanceof TargetSpell && target.getFirstTarget() != null) {
                        casted.put(getKey(target.getFirstTarget(), stackObject.getSourceId()), stackObject.getControllerId());
                    }
                }
            }
        }
    }

    private String getKey(UUID targetId, UUID sourceId) {
        return new StringBuilder(targetId.toString()).append("_").append(sourceId.toString()).toString();
    }

    public UUID getControllerSpell(UUID sourceId, UUID counteredSpell) {
        if (sourceId != null && counteredSpell != null){
            return casted.get(getKey(counteredSpell, sourceId));
        }
        return null;
    }

    @Override
    public void reset() {
        super.reset();
        casted.clear();
    }

    @Override
    public CastedSpellsWithSpellTarget copy() {
        return new CastedSpellsWithSpellTarget(this);
    }

}
