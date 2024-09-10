package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AnimPakalThousandthMoon extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("non-Gnome creatures");

    static {
        filter.add(Predicates.not(SubType.GNOME.getPredicate()));
    }

    public AnimPakalThousandthMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you attack with one or more non-Gnome creatures, put a +1/+1 counter on Anim Pakal, then create X 1/1 colorless Gnome artifact creature tokens that are tapped and attacking, where X is the number of +1/+1 counters on Anim Pakal.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), 1, filter
        );
        ability.addEffect(
                new CreateTokenEffect(
                        new GnomeToken(),
                        new CountersSourceCount(CounterType.P1P1),
                        true, true
                ).setText(", then create X 1/1 colorless Gnome artifact creature tokens that are tapped and attacking, "
                        + "where X is the number of +1/+1 counters on {this}")
        );
        this.addAbility(ability);
    }

    private AnimPakalThousandthMoon(final AnimPakalThousandthMoon card) {
        super(card);
    }

    @Override
    public AnimPakalThousandthMoon copy() {
        return new AnimPakalThousandthMoon(this);
    }
}
