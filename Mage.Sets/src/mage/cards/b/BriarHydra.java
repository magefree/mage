package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class BriarHydra extends CardImpl {

    public BriarHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Domain -- Whenever Briar Hydra deals combat damage to a player, put X +1/+1 counters on target creature you control, where X is the number of basic land types among lands you control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), DomainValue.REGULAR),
                false
        );
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BriarHydra(final BriarHydra card) {
        super(card);
    }

    @Override
    public BriarHydra copy() {
        return new BriarHydra(this);
    }
}
