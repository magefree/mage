

package mage.cards.d;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author Loki
 */
public final class DarksteelBrute extends CardImpl {

    public DarksteelBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {3}: Darksteel Brute becomes a 2/2 Beast artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 Beast artifact creature")
                        .withSubType(SubType.BEAST)
                        .withType(CardType.ARTIFACT),
                CardType.ARTIFACT, Duration.EndOfTurn), new GenericManaCost(3)));
    }

    private DarksteelBrute(final DarksteelBrute card) {
        super(card);
    }

    @Override
    public DarksteelBrute copy() {
        return new DarksteelBrute(this);
    }

}
