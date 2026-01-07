package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BurdenedStoneback extends CardImpl {

    public BurdenedStoneback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
            null, "This creature enters with two -1/-1 counters on it.", "")
        );

        // {1}{W}, Remove a counter from this creature: Target creature gains indestructible until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new RemoveCountersSourceCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private BurdenedStoneback(final BurdenedStoneback card) {
        super(card);
    }

    @Override
    public BurdenedStoneback copy() {
        return new BurdenedStoneback(this);
    }
}
