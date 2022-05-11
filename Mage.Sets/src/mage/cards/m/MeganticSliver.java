package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MeganticSliver extends CardImpl {

    public MeganticSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                3, 3, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private MeganticSliver(final MeganticSliver card) {
        super(card);
    }

    @Override
    public MeganticSliver copy() {
        return new MeganticSliver(this);
    }
}
