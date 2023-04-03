package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SculptedPerfection extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.PHYREXIAN, "Phyrexians");

    public SculptedPerfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // When Sculpted Perfection enters the battlefield, incubate 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(2)));

        // Phyrexians you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));
    }

    private SculptedPerfection(final SculptedPerfection card) {
        super(card);
    }

    @Override
    public SculptedPerfection copy() {
        return new SculptedPerfection(this);
    }
}
