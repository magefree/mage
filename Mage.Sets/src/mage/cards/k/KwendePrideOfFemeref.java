
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author TheElk801
 */
public final class KwendePrideOfFemeref extends CardImpl {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with first strike");

    static {
        filter.add(new AbilityPredicate(FirstStrikeAbility.class));
    }

    public KwendePrideOfFemeref(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Creatures you control with first strike have double strike.
        ContinuousEffect effect = new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("Creatures you control with first strike have double strike");
        effect.setDependedToType(DependencyType.AddingAbility); // effects that add first strike need to be executed first
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private KwendePrideOfFemeref(final KwendePrideOfFemeref card) {
        super(card);
    }

    @Override
    public KwendePrideOfFemeref copy() {
        return new KwendePrideOfFemeref(this);
    }
}
