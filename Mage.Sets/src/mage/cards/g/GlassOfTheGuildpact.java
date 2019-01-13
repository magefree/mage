package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlassOfTheGuildpact extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("multicolored creatures");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public GlassOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Multicolored creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)
        ));
    }

    private GlassOfTheGuildpact(final GlassOfTheGuildpact card) {
        super(card);
    }

    @Override
    public GlassOfTheGuildpact copy() {
        return new GlassOfTheGuildpact(this);
    }
}
