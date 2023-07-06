package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaldirLorienLieutenant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.ELF, "");
    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public HaldirLorienLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Haldir, Lorien Lieutenant enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {5}{G}: Until end of turn, other Elves you control gain vigilance and get +1/+1 for each +1/+1 counter on Haldir.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText("until end of turn, other Elves you control gain vigilance"), new ManaCostsImpl<>("{5}{G}"));
        ability.addEffect(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn, filter2, true, true
        ).setText("and get +1/+1 for each +1/+1 counter on {this}"));
        this.addAbility(ability);
    }

    private HaldirLorienLieutenant(final HaldirLorienLieutenant card) {
        super(card);
    }

    @Override
    public HaldirLorienLieutenant copy() {
        return new HaldirLorienLieutenant(this);
    }
}
