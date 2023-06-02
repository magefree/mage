package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanguineStatuette extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BLOOD, "a Blood token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public SanguineStatuette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // When Sanguine Statuette enters the battlefield, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));

        // Whenever you sacrifice a Blood token, you may have Sanguine Statuette become a 3/3 Vampire artifact creature with haste until end of turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 Vampire artifact creature with haste")
                        .withType(CardType.ARTIFACT)
                        .withSubType(SubType.VAMPIRE)
                        .withAbility(HasteAbility.getInstance()),
                CardType.ARTIFACT, Duration.EndOfTurn
        ).setText("have {this} become a 3/3 Vampire artifact creature with haste until end of turn"), filter, false, true));
    }

    private SanguineStatuette(final SanguineStatuette card) {
        super(card);
    }

    @Override
    public SanguineStatuette copy() {
        return new SanguineStatuette(this);
    }
}
