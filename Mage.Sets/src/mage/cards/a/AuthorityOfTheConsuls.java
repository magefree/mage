package mage.cards.a;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AuthorityOfTheConsuls extends CardImpl {

    public AuthorityOfTheConsuls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)));

        // Whenever a creature enters the battlefield under an opponent's control, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES,
                "Whenever a creature enters the battlefield under an opponent's control, you gain 1 life."
        ));
    }

    private AuthorityOfTheConsuls(final AuthorityOfTheConsuls card) {
        super(card);
    }

    @Override
    public AuthorityOfTheConsuls copy() {
        return new AuthorityOfTheConsuls(this);
    }
}
