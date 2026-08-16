package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTargetAmount;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IronFistHeroForHire extends CardImpl {

    public IronFistHeroForHire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Power-up -- {7}{R}: Iron Fist deals 5 damage divided as you choose among up to five targets. Put five +1/+1 counters on Iron Fist.
        Ability ability = new PowerUpAbility(
            new DamageMultiEffect().setText("{this} deals 5 damage divided as you choose among up to five targets"),
            new ManaCostsImpl<>("{7}{R}")
        );
        ability.addTarget(new TargetAnyTargetAmount(5));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)));
        this.addAbility(ability);
    }

    private IronFistHeroForHire(final IronFistHeroForHire card) {
        super(card);
    }

    @Override
    public IronFistHeroForHire copy() {
        return new IronFistHeroForHire(this);
    }
}
