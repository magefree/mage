package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExsanguinatorCavalry extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.KNIGHT, "a Knight you control");

    public ExsanguinatorCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever a Knight you control deals combat damage to a player, put a +1/+1 counter on that creature and create a Blood token.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                filter, false, SetTargetPointer.PERMANENT, true
        );
        ability.addEffect(new CreateTokenEffect(new BloodToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private ExsanguinatorCavalry(final ExsanguinatorCavalry card) {
        super(card);
    }

    @Override
    public ExsanguinatorCavalry copy() {
        return new ExsanguinatorCavalry(this);
    }
}
