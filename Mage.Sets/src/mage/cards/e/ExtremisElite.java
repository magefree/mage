package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ExtremisElite extends CardImpl {

    public ExtremisElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Power-up -- {4}{R}: Put two +1/+1 counters on this creature. It deals 1 damage to any target.
        Ability ability = new PowerUpAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{4}{R}"));
        ability.addEffect(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ExtremisElite(final ExtremisElite card) {
        super(card);
    }

    @Override
    public ExtremisElite copy() {
        return new ExtremisElite(this);
    }
}
