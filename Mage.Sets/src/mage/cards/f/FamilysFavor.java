package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class FamilysFavor extends CardImpl {

    public FamilysFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you attack, put a shield counter on target attacking creature.
        // Until end of turn, it gains
        //      “Whenever this creature deals combat damage to a player,
        //       remove a shield counter from it.
        //       If you do, draw a card.”
        // (If a creature with a shield counter on it would be dealt damage or destroyed, remove a shield counter from it instead.)
        Ability attacksAbility = new AttacksWithCreaturesTriggeredAbility(new AddCountersTargetEffect(CounterType.SHIELD.createInstance()), 1);
        attacksAbility.addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DoIfCostPaid(
                                new DrawCardSourceControllerEffect(1),
                                new RemoveCountersSourceCost(CounterType.SHIELD.createInstance())),
                        false),
                Duration.EndOfTurn,
                "Until end of turn, it gains " +
                        "\"Whenever this creature deals combat damage to a player, remove a shield counter from it. " +
                        "If you do, draw a card.\""
                ).setText("Until end of turn, it gains " +
                            "\"Whenever this creature deals combat damage to a player, remove a shield counter from it. " +
                            "If you do, draw a card.\" " +
                          "<i>(If a creature with a shield counter on it would be dealt damage or destroyed, remove a shield counter from it instead.)</i>"
                )
        );

        attacksAbility.addTarget(new TargetAttackingCreature());
        this.addAbility(attacksAbility);
    }

    private FamilysFavor(final FamilysFavor card) {
        super(card);
    }

    @Override
    public FamilysFavor copy() {
        return new FamilysFavor(this);
    }
}
