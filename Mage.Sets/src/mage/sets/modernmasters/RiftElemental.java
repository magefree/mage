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
package mage.sets.modernmasters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RiftElemental extends CardImpl<RiftElemental> {

    private static final FilterCard filter = new FilterCard("suspended card you own");
    static {
        filter.add(new CounterPredicate(CounterType.TIME));
        filter.add(new AbilityPredicate(SuspendAbility.class));
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public RiftElemental(UUID ownerId) {
        super(ownerId, 127, "Rift Elemental", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}, Remove a time counter from a permanent you control or suspended card you own: Rift Elemental gets +2/+0 until end of turn.
        Choice targetChoice = new ChoiceImpl();
        targetChoice.setMessage("Choose what to target");
        targetChoice.getChoices().add("Permanent");
        targetChoice.getChoices().add("Suspended Card");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,0,Duration.EndOfTurn), new ManaCostsImpl("{1}{R}"));
        ability.addChoice(targetChoice);
        ability.addCost(new RemoveCounterFromCardCost(new TargetCardInExile(1,1,filter, null, true), CounterType.TIME));
        this.addAbility(ability);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof ActivatedAbility && !ability.getChoices().isEmpty()) {
            ability.getCosts().clear();
            Choice targetChoice = ability.getChoices().get(0);
            if (targetChoice.getChoice().equals("Permanent")) {
                ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.TIME));
            }
            if (targetChoice.getChoice().equals("Suspended Card")) {
                ability.addCost(new RemoveCounterFromCardCost(new TargetCardInExile(1,1,filter, null, true), CounterType.TIME));
            }
        }
    }

    public RiftElemental(final RiftElemental card) {
        super(card);
    }

    @Override
    public RiftElemental copy() {
        return new RiftElemental(this);
    }
}

class RemoveCounterFromCardCost extends CostImpl<RemoveCounterFromCardCost> {

    private TargetCard target;
    private String name;
    private CounterType counterTypeToRemove;

    public RemoveCounterFromCardCost(TargetCard target) {
        this(target, null);
    }

    public RemoveCounterFromCardCost(TargetCard target, CounterType counterTypeToRemove) {
        this.target = target;
        this.counterTypeToRemove = counterTypeToRemove;
        text = setText();
    }

    public RemoveCounterFromCardCost(final RemoveCounterFromCardCost cost) {
        super(cost);
        this.target = cost.target.copy();
        this.name = cost.name;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        paid = false;
        Player controller = game.getPlayer(controllerId);
        if (target.choose(Outcome.UnboostCreature, controllerId, sourceId, game)) {
            for (UUID targetId: (List<UUID>)target.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    if (card.getCounters().size() > 0 && (counterTypeToRemove == null || card.getCounters().containsKey(counterTypeToRemove))) {
                        String counterName = null;
                        if (counterTypeToRemove != null) {
                            counterName = counterTypeToRemove.getName();
                        } else {
                            if (card.getCounters().size() > 1 && counterTypeToRemove == null) {
                                Choice choice = new ChoiceImpl(true);
                                Set<String> choices = new HashSet<String>();
                                for (Counter counter : card.getCounters().values()) {
                                    if (card.getCounters().getCount(counter.getName()) > 0) {
                                        choices.add(counter.getName());
                                    }
                                }
                                choice.setChoices(choices);
                                choice.setMessage("Choose a counter to remove from " + card.getName());
                                controller.choose(Outcome.UnboostCreature, choice, game);
                                counterName = choice.getChoice();
                            } else {
                                for (Counter counter : card.getCounters().values()) {
                                    if (counter.getCount() > 0) {
                                        counterName = counter.getName();
                                    }
                                }
                            }
                        }
                        if (counterName != null) {
                            card.removeCounters(counterName, 1, game);
                            if (card.getCounters().getCount(counterName) == 0 ){
                                card.getCounters().removeCounter(counterName);
                            }
                            this.paid = true;
                            game.informPlayers(new StringBuilder(controller.getName()).append(" removes a ").append(counterName).append(" counter from ").append(card.getName()).toString());
                        }
                    }
                }
            }
        }
        target.clearChosen();
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    private String setText() {
        return "Remove a time counter from a permanent you control or suspended card you own";
    }

    @Override
    public RemoveCounterFromCardCost copy() {
        return new RemoveCounterFromCardCost(this);
    }
}
