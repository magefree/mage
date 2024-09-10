package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazotepSliver extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.SLIVER, "a nontoken Sliver you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public LazotepSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Sliver creatures you control have afflict 2.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AfflictAbility(2), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS
        ).setText("Sliver creatures you control have afflict 2")));

        // Whenever a nontoken Sliver you control dies, amass Slivers 2.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AmassEffect(2, SubType.SLIVER), false, filter
        ));
    }

    private LazotepSliver(final LazotepSliver card) {
        super(card);
    }

    @Override
    public LazotepSliver copy() {
        return new LazotepSliver(this);
    }
}
