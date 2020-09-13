package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrollbredGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public TrollbredGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {2}{G}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{G}"));

        // Each creature you control with a +1/+1 counter on it has trample.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        filter, "Each creature you control " +
                        "with a +1/+1 counter on it has trample"
                )
        ));
    }

    private TrollbredGuardian(final TrollbredGuardian card) {
        super(card);
    }

    @Override
    public TrollbredGuardian copy() {
        return new TrollbredGuardian(this);
    }
}
