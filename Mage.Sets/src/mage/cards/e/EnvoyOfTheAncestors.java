package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnvoyOfTheAncestors extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("modified creatures");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public EnvoyOfTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Outlast {W}
        this.addAbility(new OutlastAbility(new ManaCostsImpl<>("{W}")));

        // Modified creatures you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, filter
        )));
    }

    private EnvoyOfTheAncestors(final EnvoyOfTheAncestors card) {
        super(card);
    }

    @Override
    public EnvoyOfTheAncestors copy() {
        return new EnvoyOfTheAncestors(this);
    }
}
