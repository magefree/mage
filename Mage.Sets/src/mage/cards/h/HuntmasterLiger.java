package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourceMutatedCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntmasterLiger extends CardImpl {

    public HuntmasterLiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Mutate {2}{W}
        this.addAbility(new MutateAbility(this, "{2}{W}"));

        // Whenever this creature mutates, other creatures you control get +X/+X until end of turn, where X is the number of times this creature has mutated.
        this.addAbility(new MutatesSourceTriggeredAbility(new BoostControlledEffect(
                SourceMutatedCount.instance, SourceMutatedCount.instance, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true, true
        )));
    }

    private HuntmasterLiger(final HuntmasterLiger card) {
        super(card);
    }

    @Override
    public HuntmasterLiger copy() {
        return new HuntmasterLiger(this);
    }
}
// it's like a lion and a tiger mixed... bred for its skills in magic
