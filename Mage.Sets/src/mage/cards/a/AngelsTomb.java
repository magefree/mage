
package mage.cards.a;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class AngelsTomb extends CardImpl {

    public AngelsTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature enters the battlefield under your control, you may have Angel's Tomb become a 3/3 white Angel artifact creature with flying until end of turn.
        Effect effect = new BecomesCreatureSourceEffect(new CreatureToken(3, 3, "3/3 white Angel artifact creature with flying")
                .withColor("W")
                .withSubType(SubType.ANGEL)
                .withAbility(FlyingAbility.getInstance()),
                CardType.ARTIFACT, Duration.EndOfTurn)
                .setText("have {this} become a 3/3 white Angel artifact creature with flying until end of turn");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                effect,
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                true)
        );
    }

    private AngelsTomb(final AngelsTomb card) {
        super(card);
    }

    @Override
    public AngelsTomb copy() {
        return new AngelsTomb(this);
    }
}
