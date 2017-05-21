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
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class WeightOfConscience extends CardImpl {

    public WeightOfConscience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAttachedEffect(AttachmentType.AURA)));

        // Tap two untapped creatures you control that share a creature type: Exile enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WeightOfConscienceEffect(), new TapTargetCost(new WeightOfConscienceTarget())));
    }

    public WeightOfConscience(final WeightOfConscience card) {
        super(card);
    }

    @Override
    public WeightOfConscience copy() {
        return new WeightOfConscience(this);
    }
}

class WeightOfConscienceEffect extends OneShotEffect {

    WeightOfConscienceEffect() {
        super(Outcome.Exile);
        staticText = "Exile enchanted creature";
    }

    WeightOfConscienceEffect(final WeightOfConscienceEffect effect) {
        super(effect);
    }

    @Override
    public WeightOfConscienceEffect copy() {
        return new WeightOfConscienceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                controller.moveCardsToExile(creature, source, game, true, null, "");
            }
        }
        return false;
    }
}

class WeightOfConscienceTarget extends TargetControlledCreaturePermanent {

    private static final FilterControlledCreaturePermanent filterUntapped = new FilterControlledCreaturePermanent("untapped creatures you control that share a creature type");
    static {
        filterUntapped.add(Predicates.not(new TappedPredicate()));
    }

    WeightOfConscienceTarget() {
        super(2, 2, filterUntapped, true);
    }

    WeightOfConscienceTarget(final WeightOfConscienceTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Player player = game.getPlayer(sourceControllerId);
        Set<UUID> possibleTargets = new HashSet<>(0);
        if (player != null) {
            // Choosing first target
            if (this.getTargets().isEmpty()) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filterUntapped, sourceControllerId, game)) {
                    for (String subtype : permanent.getSubtype(game)) {
                        if (!CardUtil.isNonCreatureSubtype(subtype)) {
                            if (game.getBattlefield().contains(new FilterControlledCreaturePermanent(SubType.byDescription(subtype), subtype), sourceControllerId, game, 2)) {
                                possibleTargets.add(permanent.getId());
                            }
                        }
                    }
                }
            }
            // Choosing second target
            else {
                UUID firstTargetId = this.getTargets().get(0);
                Permanent firstTargetCreature = game.getPermanent(firstTargetId);
                if (firstTargetCreature != null) {
                    for (Permanent permanent : game.getBattlefield().getActivePermanents(filterUntapped, sourceControllerId, game)) {
                        if (!permanent.getId().equals(firstTargetId) && firstTargetCreature.shareSubtypes(permanent, game)) {
                            possibleTargets.add(permanent.getId());
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        for (Permanent permanent1 : game.getBattlefield().getActivePermanents(filterUntapped, sourceControllerId, game)) {
            for (Permanent permanent2 : game.getBattlefield().getActivePermanents(filterUntapped, sourceControllerId, game)) {
                if (!Objects.equals(permanent1, permanent2) && permanent1.shareSubtypes(permanent2, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (super.canTarget(id, game)) {
            Permanent targetPermanent = game.getPermanent(id);
            if (targetPermanent != null) {
                if (this.getTargets().isEmpty()) {
                    for (Permanent permanent : game.getBattlefield().getActivePermanents(filterUntapped, source.getControllerId(), game)) {
                        for (String subtype : permanent.getSubtype(game)) {
                            if (!CardUtil.isNonCreatureSubtype(subtype)) {
                                if (game.getBattlefield().contains(new FilterControlledCreaturePermanent(SubType.byDescription(subtype), subtype), source.getControllerId(), game, 2)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                else {
                    Permanent firstTarget = game.getPermanent(this.getTargets().get(0));
                    if (firstTarget != null && firstTarget.shareSubtypes(targetPermanent, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public WeightOfConscienceTarget copy() {
        return new WeightOfConscienceTarget(this);
    }
}
