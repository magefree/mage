package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianAwakening extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PHYREXIAN, "Phyrexians");

    public PhyrexianAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Phyrexian Awakening enters the battlefield, incubate 4.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(4)));

        // Phyrexians you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private PhyrexianAwakening(final PhyrexianAwakening card) {
        super(card);
    }

    @Override
    public PhyrexianAwakening copy() {
        return new PhyrexianAwakening(this);
    }
}
