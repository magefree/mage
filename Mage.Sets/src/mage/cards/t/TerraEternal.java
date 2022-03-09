package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class TerraEternal extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("all lands");

    public TerraEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // All lands are indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false
        )));
    }

    private TerraEternal(final TerraEternal card) {
        super(card);
    }

    @Override
    public TerraEternal copy() {
        return new TerraEternal(this);
    }
}
