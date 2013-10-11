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
package mage.sets.conflux;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class CorruptedRoots extends CardImpl<CorruptedRoots> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest or Plains");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate("Forest"),
                new SubtypePredicate("Plains")));
    }

    public CorruptedRoots(UUID ownerId) {
        super(ownerId, 41, "Corrupted Roots", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.expansionSetCode = "CON";
        this.subtype.add("Aura");

        this.color.setBlack(true);

        // Enchant Forest or Plains
        TargetPermanent auraTarget = new TargetLandPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted land becomes tapped, its controller loses 2 life.
        this.addAbility(new CorruptedRootsTriggeredAbility());

    }

    public CorruptedRoots(final CorruptedRoots card) {
        super(card);
    }

    @Override
    public CorruptedRoots copy() {
        return new CorruptedRoots(this);
    }
}

class CorruptedRootsTriggeredAbility extends TriggeredAbilityImpl<CorruptedRootsTriggeredAbility> {

    CorruptedRootsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2));
    }

    CorruptedRootsTriggeredAbility(final CorruptedRootsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent enchantment = game.getPermanent(this.sourceId);
            if (enchantment != null && enchantment.getAttachedTo().equals(event.getTargetId())) {
                Permanent attached = game.getPermanent(enchantment.getAttachedTo());
                if (attached != null) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(attached.getControllerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CorruptedRootsTriggeredAbility copy() {
        return new CorruptedRootsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted land becomes tapped, its controller loses 2 life.";
    }
}