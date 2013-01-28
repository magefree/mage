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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class RustScarab extends CardImpl<RustScarab> {

    public RustScarab(UUID ownerId) {
        super(ownerId, 130, "Rust Scarab", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Insect");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Rust Scarab becomes blocked, you may destroy target artifact or enchantment defending player controls.
        Effect effect = new DestroyTargetEffect();
        effect.setText("destroy target artifact or enchantment defending player controls");
        this.addAbility(new BecomesBlockedTriggeredAbility(effect, true));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {

    }

    public RustScarab(final RustScarab card) {
        super(card);
    }

    @Override
    public RustScarab copy() {
        return new RustScarab(this);
    }
}


class BecomesBlockedTriggeredAbility extends TriggeredAbilityImpl<BecomesBlockedTriggeredAbility> {

    public BecomesBlockedTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesBlockedTriggeredAbility(final BecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATURE_BLOCKED && event.getTargetId().equals(this.getSourceId())) {
            UUID defenderId = game.getState().getCombat().findGroup(this.getSourceId()).getDefenderId();
            if (defenderId != null) {
                this.getTargets().clear();
                FilterPermanent filter = new FilterPermanent("artifact or enchantment defending player controls");
                filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.ENCHANTMENT)));
                filter.add(new ControllerIdPredicate(defenderId));
                Target target = new TargetPermanent(filter);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked, " + super.getRule();
    }

    @Override
    public BecomesBlockedTriggeredAbility copy() {
        return new BecomesBlockedTriggeredAbility(this);
    }
}
