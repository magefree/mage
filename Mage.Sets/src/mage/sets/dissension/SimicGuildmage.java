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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public class SimicGuildmage extends CardImpl {
    
    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura");

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public SimicGuildmage(UUID ownerId) {
        super(ownerId, 148, "Simic Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G/U}{G/U}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Elf");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}: Move a +1/+1 counter from target creature onto another target creature with the same controller.
        Ability countersAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveCounterFromTargetToTargetEffect(), new ManaCostsImpl("{1}{G}"));
        TargetCreaturePermanent target = new TargetCreaturePermanent(
                new FilterCreaturePermanent("creature (you take counter from)"));
        target.setTargetTag(1);
        countersAbility.addTarget(target);
        
        FilterCreaturePermanent filter = new FilterCreaturePermanent(
                "another target creature with the same controller (counter goes to)");
        filter.add(new AnotherTargetPredicate(2));
        filter.add(new SameControllerPredicate());
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        countersAbility.addTarget(target2);
        this.addAbility(countersAbility);
        // {1}{U}: Attach target Aura enchanting a permanent to another permanent with the same controller.
        Ability auraAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveAuraEffect(), new ManaCostsImpl("{1}{U}"));
        auraAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(auraAbility);

        
    }

    public SimicGuildmage(final SimicGuildmage card) {
        super(card);
    }

    @Override
    public SimicGuildmage copy() {
        return new SimicGuildmage(this);
    }
}

class MoveCounterFromTargetToTargetEffect extends OneShotEffect {

    public MoveCounterFromTargetToTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Move a +1/+1 counter from target creature onto another target creature with the same controller";
    }

    public MoveCounterFromTargetToTargetEffect(final MoveCounterFromTargetToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToTargetEffect copy() {
        return new MoveCounterFromTargetToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent toPermanent = null;
            if (source.getTargets().size() > 1) {
                toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            }
            if (fromPermanent == null || toPermanent == null || !fromPermanent.getControllerId().equals(toPermanent.getControllerId())) {
                return false;
            }
            fromPermanent.removeCounters(CounterType.P1P1.createInstance(1), game);
            toPermanent.addCounters(CounterType.P1P1.createInstance(1), game);
            return true;
        }
        return false;

    }
}

class SameControllerPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageItem>> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source != null) {
            if (source.getStackAbility().getTargets().isEmpty()
                || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
                return true;
            }
            Permanent firstTarget = game.getPermanent(
                    source.getStackAbility().getTargets().get(0).getTargets().get(0));
            Permanent inputPermanent = game.getPermanent(input.getObject().getId());
            if (firstTarget != null && inputPermanent != null) {
                return firstTarget.getControllerId().equals(inputPermanent.getControllerId());
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Target with the same controller";
    }
    
}

class MoveAuraEffect extends OneShotEffect {

    public MoveAuraEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Attach target Aura enchanting a permanent to another permanent with the same controller.";
    }

    public MoveAuraEffect(final MoveAuraEffect effect) {
        super(effect);
    }

    @Override
    public MoveAuraEffect copy() {
        return new MoveAuraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
            5/1/2006	
            For the second ability, only the Aura is targeted. 
            When the ability resolves, you choose a permanent to move the Aura onto. 
            It can’t be the permanent the Aura is already attached to, it must be controlled by the player who controls the permanent the Aura is attached to, and it must be able to be enchanted by the Aura. 
            (It doesn’t matter who controls the Aura or who controls Simic Guildmage.) 
            If no such permanent exists, the Aura doesn’t move.         
        */
        UUID auraId = getTargetPointer().getFirst(game, source);
        Permanent aura = game.getPermanent(auraId);
        Permanent fromPermanent = game.getPermanent(aura.getAttachedTo());
        Player controller = game.getPlayer(source.getControllerId());
        if (fromPermanent != null && controller != null) {
            Boolean passed = true;
            FilterPermanent filterChoice = new FilterPermanent("a different permanent with the same controller as the target to attach the enchantments to");
            filterChoice.add(new ControllerIdPredicate(fromPermanent.getControllerId()));
            filterChoice.add(Predicates.not(new PermanentIdPredicate(fromPermanent.getId())));

            Target chosenPermanentToAttachAuras = new TargetPermanent(filterChoice);
            chosenPermanentToAttachAuras.setNotTarget(true);

            if (chosenPermanentToAttachAuras.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.choose(Outcome.Neutral, chosenPermanentToAttachAuras, source.getSourceId(), game)) {
                Permanent permanentToAttachAuras = game.getPermanent(chosenPermanentToAttachAuras.getFirstTarget());
                if (permanentToAttachAuras != null) {
                    if (aura != null && passed) {
                        // Check the target filter
                        Target target = aura.getSpellAbility().getTargets().get(0);
                        if (target instanceof TargetPermanent) {
                            if (!target.getFilter().match(permanentToAttachAuras, game)) {
                                passed = false;
                            }
                        }
                        // Check for protection
                        MageObject auraObject = game.getObject(auraId);
                        if (permanentToAttachAuras.cantBeAttachedBy(auraObject, game)) {
                            passed = false;
                        }
                    }
                    if (passed) {
                        fromPermanent.removeAttachment(aura.getId(), game);
                        permanentToAttachAuras.addAttachment(aura.getId(), game);
                        return true;
                    }
                }
            }
            return true;
        }

        return false;
    }
}