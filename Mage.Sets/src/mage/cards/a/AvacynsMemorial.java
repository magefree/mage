package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvacynsMemorial extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("legendary permanents");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public AvacynsMemorial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{W}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Other legendary permanents you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private AvacynsMemorial(final AvacynsMemorial card) {
        super(card);
    }

    @Override
    public AvacynsMemorial copy() {
        return new AvacynsMemorial(this);
    }
}
