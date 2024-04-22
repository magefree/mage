package mage.cards.c;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChimilTheInnerSun extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spells you control");

    public ChimilTheInnerSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);

        // Spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(
                new CantBeCounteredControlledEffect(filter, null, Duration.WhileOnBattlefield)
        ));

        // At the beginning of your end step, discover 5.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DiscoverEffect(5), TargetController.YOU, false
        ));
    }

    private ChimilTheInnerSun(final ChimilTheInnerSun card) {
        super(card);
    }

    @Override
    public ChimilTheInnerSun copy() {
        return new ChimilTheInnerSun(this);
    }
}
