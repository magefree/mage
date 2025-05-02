package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonbackAssault extends CardImpl {

    public DragonbackAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}{R}");

        // When this enchantment enters, it deals 3 damage to each creature and each planeswalker.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(
                3, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER
        ).setText("it deals 3 damage to each creature and each planeswalker")));

        // Landfall -- Whenever a land you control enters, create a 4/4 red Dragon creature token with flying.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new DragonToken())));
    }

    private DragonbackAssault(final DragonbackAssault card) {
        super(card);
    }

    @Override
    public DragonbackAssault copy() {
        return new DragonbackAssault(this);
    }
}
