package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VillageWatch extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent("Wolves and Werewolves");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public VillageWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{R}",
                "Village Reavers",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Village Watch
        this.getLeftHalfCard().setPT(4, 3);

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Village Reavers
        this.getRightHalfCard().setPT(5, 4);

        // Wolves and Werewolves you control have haste.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private VillageWatch(final VillageWatch card) {
        super(card);
    }

    @Override
    public VillageWatch copy() {
        return new VillageWatch(this);
    }
}
