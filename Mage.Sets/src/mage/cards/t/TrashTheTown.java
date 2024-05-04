package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrashTheTown extends CardImpl {

    public TrashTheTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2} -- Put two +1/+1 counters on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(2));

        // + {1} -- Target creature gains trample until end of turn.
        this.getSpellAbility().addMode(new Mode(new GainAbilityTargetEffect(TrampleAbility.getInstance()))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new GenericManaCost(1)));

        // + {1} -- Until end of turn, target creature gains "Whenever this creature deals combat damage to a player, draw two cards."
        this.getSpellAbility().addMode(new Mode(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(2), false
                ).setTriggerPhrase("Whenever this creature deals combat damage to a player, ")
        ).setText("Until end of turn, target creature gains \"Whenever this creature deals combat damage to a player, draw two cards.\""))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new GenericManaCost(1)));
    }

    private TrashTheTown(final TrashTheTown card) {
        super(card);
    }

    @Override
    public TrashTheTown copy() {
        return new TrashTheTown(this);
    }
}
