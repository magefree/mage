package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.ReachAbility;
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
public final class TangledSkyline extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PHYREXIAN, "Phyrexians");

    public TangledSkyline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // When Tangled Skyline enters the battlefield, you gain 5 life and incubate 5.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5));
        ability.addEffect(new IncubateEffect(5).concatBy("and"));
        this.addAbility(ability);

        // Phyrexians you control have reach.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ReachAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private TangledSkyline(final TangledSkyline card) {
        super(card);
    }

    @Override
    public TangledSkyline copy() {
        return new TangledSkyline(this);
    }
}
