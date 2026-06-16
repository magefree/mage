package mage.cards.q;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class QuantumEntanglement extends CardImpl {

    public QuantumEntanglement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");


        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this enchantment enters and at the beginning of your end step, you may pay {1}{W}.
        // When you do, exile target creature you control, then return that card to the battlefield under its owner's control.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ExileThenReturnTargetEffect(false, true), false
        );
        reflexive.addTarget(new TargetControlledCreaturePermanent());

        Ability ability = new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new DoWhenCostPaid(reflexive, new ManaCostsImpl<>("{1}{W}"), "Pay {1}{W}?"),
            new EntersBattlefieldTriggeredAbility(null),
            new BeginningOfEndStepTriggeredAbility(null)
        ).setTriggerPhrase("When {this} enters and at the beginning of your end step, ");
        this.addAbility(ability);
    }

    private QuantumEntanglement(final QuantumEntanglement card) {
        super(card);
    }

    @Override
    public QuantumEntanglement copy() {
        return new QuantumEntanglement(this);
    }
}
