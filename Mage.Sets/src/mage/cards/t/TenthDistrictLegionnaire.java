package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TenthDistrictLegionnaire extends CardImpl {

    public TenthDistrictLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast a spell that targets Tenth District Legionnaire, put a +1/+1 counter on Tenth District Legionnaire, then scry 1.
        Ability ability = new HeroicAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()
        ), false, false);
        ability.addEffect(new ScryEffect(1, false).concatBy(", then"));
        this.addAbility(ability);
    }

    private TenthDistrictLegionnaire(final TenthDistrictLegionnaire card) {
        super(card);
    }

    @Override
    public TenthDistrictLegionnaire copy() {
        return new TenthDistrictLegionnaire(this);
    }
}
