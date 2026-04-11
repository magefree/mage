package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FractalToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class EmilVastlandsRoamer extends CardImpl {

    private static final DifferentlyNamedPermanentCount xValue = new DifferentlyNamedPermanentCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    public EmilVastlandsRoamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Creatures you control with +1/+1 counters on them have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_CONTROLLED_CREATURES_P1P1
        ).setText("creatures you control with +1/+1 counters on them have trample")));

        // {4}{G}, {T}: Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is the number of differently named lands you control.
        Ability ability = new SimpleActivatedAbility(FractalToken.getEffect(
            xValue,
            ". Put X +1/+1 counters on it, where X is the number of differently named lands you control"
        ), new ManaCostsImpl<>("{4}{G}")).addHint(xValue.getHint());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private EmilVastlandsRoamer(final EmilVastlandsRoamer card) {
        super(card);
    }

    @Override
    public EmilVastlandsRoamer copy() {
        return new EmilVastlandsRoamer(this);
    }
}
