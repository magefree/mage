package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingedHiveTyrant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public WingedHiveTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // The Will of the Hive Mind -- Other creatures you control with counters on them have flying and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityAllEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("and haste"));
        this.addAbility(ability.withFlavorWord("The Will of the Hive Mind"));
    }

    private WingedHiveTyrant(final WingedHiveTyrant card) {
        super(card);
    }

    @Override
    public WingedHiveTyrant copy() {
        return new WingedHiveTyrant(this);
    }
}
