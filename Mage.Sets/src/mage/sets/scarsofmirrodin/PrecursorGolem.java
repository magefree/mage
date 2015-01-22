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
package mage.sets.scarsofmirrodin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.filter.predicate.mageobject.FromSetPredicate;
import mage.target.TargetPermanent;
import mage.util.SpellTargetAddress;

/**
 * @author nantuko
 */
public class PrecursorGolem extends CardImpl {

    public PrecursorGolem(UUID ownerId) {
        super(ownerId, 194, "Precursor Golem", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Golem");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Precursor Golem enters the battlefield, put two 3/3 colorless Golem artifact creature tokens onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken(), 2), false));

        // Whenever a player casts an instant or sorcery spell that targets only a single Golem, that player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems.
        this.addAbility(new PrecursorGolemCopyTriggeredAbility());
    }

    public PrecursorGolem(final PrecursorGolem card) {
        super(card);
    }

    @Override
    public PrecursorGolem copy() {
        return new PrecursorGolem(this);
    }
}

class PrecursorGolemCopyTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    PrecursorGolemCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PrecursorGolemCopySpellEffect(), false);
    }

    PrecursorGolemCopyTriggeredAbility(final PrecursorGolemCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrecursorGolemCopyTriggeredAbility copy() {
        return new PrecursorGolemCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (checkSpell(spell, game)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell != null &&
            (spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))) {
            UUID targetGolem = null;
            for (SpellTargetAddress addr : SpellTargetAddress.walk(spell)) {
                Target targetInstance = addr.getTarget(spell);
                for (UUID target : targetInstance.getTargets()) {
                    Permanent permanent = game.getPermanent(target);
                    if (permanent == null || !permanent.hasSubtype("Golem")) {
                        return false;
                    }
                    if (targetGolem == null) {
                        targetGolem = target;
                    } else {
                        // If a spell has multiple targets, but it's targeting the same Golem with all of them, Precursor Golem's last ability will trigger
                        if (!targetGolem.equals(target)) {
                            return false;
                        }
                    }
                }
            }
            if (targetGolem != null) {
                getEffects().get(0).setValue("triggeringSpell", spell);
                getEffects().get(0).setValue("targetedGolem", targetGolem);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an instant or sorcery spell that targets only a single Golem, that player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems";
    }
}

class PrecursorGolemCopySpellEffect extends OneShotEffect {

    private static final FilterPermanent filterGolem = new FilterPermanent();

    static {
        filterGolem.add(new SubtypePredicate("Golem"));
    }

    public PrecursorGolemCopySpellEffect() {
        super(Outcome.Copy);
    }

    public PrecursorGolemCopySpellEffect(final PrecursorGolemCopySpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("triggeringSpell");
        if (spell != null) {
            UUID targetedGolem = (UUID) getValue("targetedGolem");
            Map<UUID, Spell> targetable = new HashMap<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterGolem, source.getControllerId(), source.getSourceId(), game)) {
                if (permanent.getId().equals(targetedGolem)) {
                    continue; // copy only for other golems
                }
                boolean legal = true;
                for (SpellTargetAddress addr : SpellTargetAddress.walk(spell)) {
                    Target target = addr.getTarget(spell);
                    if (!target.canTarget(permanent.getId(), game)) {
                        legal = false;
                        break;
                    }
                }
                if (legal) {
                    Spell copy = spell.copySpell();
                    copy.setCopiedSpell(true);
                    for (SpellTargetAddress addr : SpellTargetAddress.walk(copy)) {
                        Target target = addr.getTarget(copy);
                        target.clearChosen();
                        target.add(permanent.getId(), game);
                    }
                    targetable.put(permanent.getId(), copy);
                }
            }
            UUID spellController = spell.getControllerId();
            while (targetable.size() > 0) {
                FilterPermanent filter = new FilterPermanent("Golem",
                                                             "Golem that spell could target ("+Integer.toString(targetable.size())+" remaining)");
                filter.add(new FromSetPredicate(targetable.keySet()));
                TargetPermanent target = new TargetPermanent(0, 1, filter, true);

                if (target.possibleTargets(spellController, game).size() > 1
                    && target.canChoose(spell.getSourceId(), spellController, game)) {
                    game.getPlayer(spellController).choose(Outcome.Neutral, target, source.getId(), game);
                }
                Collection<UUID> chosen = target.getTargets();
                if (chosen.size() == 0) {
                    chosen = targetable.keySet();
                }
                List<UUID> toDelete = new ArrayList<>();
                for (UUID chosenId : chosen) {
                    Spell chosenCopy = targetable.get(chosenId);
                    if (chosenCopy != null) {
                        game.getStack().push(chosenCopy);
                        toDelete.add(chosenId);
                    }
                }
                for (UUID id : toDelete) {
                    targetable.remove(id);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PrecursorGolemCopySpellEffect copy() {
        return new PrecursorGolemCopySpellEffect(this);
    }

}

