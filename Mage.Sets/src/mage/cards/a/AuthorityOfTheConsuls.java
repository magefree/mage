package mage.cards.a;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AuthorityOfTheConsuls extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    public AuthorityOfTheConsuls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));

        // Whenever a creature enters the battlefield under an opponent's control, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new GainLifeEffect(1), filter, "Whenever a creature enters " +
                "the battlefield under an opponent's control, you gain 1 life."
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
