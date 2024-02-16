package mage.cards.a;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngrathCaptainOfChaos extends CardImpl {

    public AngrathCaptainOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B/R}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGRATH);
        this.setStartingLoyalty(5);

        // Creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // -2: Amass 2. (Put two +1/+1 counters on an Army you control. If you don’t control one, create a 0/0 black Zombie Army creature token first.)
        this.addAbility(new LoyaltyAbility(new AmassEffect(2, SubType.ZOMBIE), -2));
    }

    private AngrathCaptainOfChaos(final AngrathCaptainOfChaos card) {
        super(card);
    }

    @Override
    public AngrathCaptainOfChaos copy() {
        return new AngrathCaptainOfChaos(this);
    }
}
