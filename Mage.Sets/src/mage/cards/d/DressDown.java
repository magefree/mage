package mage.cards.d;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.events.GameEvent;

/**
 *
 * @author weirddan455
 */
public final class DressDown extends CardImpl {

    public DressDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Dress Down enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Creatures lose all abilities.
        this.addAbility(new SimpleStaticAbility(new LoseAllAbilitiesAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.WhileOnBattlefield)));

        // At the beginning of the end step, sacrifice Dress Down.
        this.addAbility(new OnEventTriggeredAbility(
                GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step",
                true, new SacrificeSourceEffect()
        ));
    }

    private DressDown(final DressDown card) {
        super(card);
    }

    @Override
    public DressDown copy() {
        return new DressDown(this);
    }
}
