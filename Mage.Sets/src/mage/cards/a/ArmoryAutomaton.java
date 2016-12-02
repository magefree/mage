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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public class ArmoryAutomaton extends CardImpl {

    public ArmoryAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        
        this.subtype.add("Construct");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Armory Automaton enters the battlefield or attacks, attach any number of target Equipments to it.
        this.addAbility(new ArmoryAutomatonAbility());
    }

    public ArmoryAutomaton(final ArmoryAutomaton card) {
        super(card);
    }

    @Override
    public ArmoryAutomaton copy() {
        return new ArmoryAutomaton(this);
    }
}

class ArmoryAutomatonEffect extends OneShotEffect {

    public ArmoryAutomatonEffect() {
        super(Outcome.Benefit);
        this.staticText = "Whenever {this} enters the battlefield or attacks, attach any number of target Equipments to it";
    }

    public ArmoryAutomatonEffect(final ArmoryAutomatonEffect effect) {
        super(effect);
    }

    @Override
    public ArmoryAutomatonEffect copy() {
        return new ArmoryAutomatonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID aautomaton = source.getSourceId();
        Player player = game.getPlayer(source.getControllerId());
        
        FilterPermanent filterEquipment = new FilterPermanent("Equipment");

        filterEquipment.add(new CardTypePredicate(CardType.ARTIFACT));
        filterEquipment.add(new SubtypePredicate("Equipment"));
        
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        
        int countBattlefield = game.getBattlefield().getAllActivePermanents(filterEquipment, game).size() - permanent.getAttachments().size();
        while (player.canRespond() 
                && countBattlefield > 0
                && player.chooseUse(Outcome.Benefit, "Attach a target Equipment?", source, game)) {
            Target targetEquipment = new TargetPermanent(filterEquipment);
            if (player.choose(Outcome.Benefit, targetEquipment, source.getSourceId(), game)) {
                Permanent aura = game.getPermanent(targetEquipment.getFirstTarget());
                if (aura != null) {
                    Permanent attachedTo = game.getPermanent(aura.getAttachedTo());
                    if (attachedTo != null) {
                        attachedTo.removeAttachment(aura.getId(), game);
                    }
                    permanent.addAttachment(aura.getId(), game);
                }
            }
            countBattlefield = game.getBattlefield().getAllActivePermanents(filterEquipment, game).size() - permanent.getAttachments().size();
        }

        return true;
    }
}

class ArmoryAutomatonAbility extends TriggeredAbilityImpl {

    public ArmoryAutomatonAbility() {
        super(Zone.BATTLEFIELD, new ArmoryAutomatonEffect(), false);
    }

    public ArmoryAutomatonAbility(final ArmoryAutomatonAbility ability) {
        super(ability);
    }

    @Override
    public ArmoryAutomatonAbility copy() {
        return new ArmoryAutomatonAbility(this);
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
        return super.getRule();
    }
}
